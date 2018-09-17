package com.ebank.model.service.impl;

import com.ebank.model.entity.*;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.repository.TransferRepository;
import com.ebank.model.request.InternalTransferWithAccountNoRequest;
import com.ebank.model.service.*;
import com.ebank.util.FeeConstant;
import com.ebank.util.RateConstant;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final BankAccountService bankAccountService;
    private final UserAccountService userAccountService;
    private final UserService userService;
    private final BankService bankService;
    private final CurrencyService currencyService;

    @Inject
    public TransferServiceImpl(TransferRepository transferRepository, BankAccountService bankAccountService, BankService bankService, UserService userService, UserAccountService userAccountService, CurrencyService currencyService) {
        this.transferRepository = transferRepository;
        this.bankAccountService = bankAccountService;
        this.userAccountService = userAccountService;
        this.currencyService = currencyService;
        this.bankService = bankService;
        this.userService = userService;
    }

    @Override
    public List<InternalTransferWithAccountNoRequest> getAll() {
        return this.transferRepository.getAll();
    }

    @Override
    public InternalTransferWithAccountNoRequest getById(long id) {
        return this.transferRepository.getById(id);
    }

    @Override
    public InternalTransferWithAccountNoRequest create(InternalTransferWithAccountNoRequest internalTransferWithAccountNo) {
        return this.transferRepository.create(internalTransferWithAccountNo);
    }

    @Override
    public InternalTransferWithAccountNoRequest update(InternalTransferWithAccountNoRequest internalTransferWithAccountNo) {
        return this.transferRepository.update(internalTransferWithAccountNo);
    }

    @Override
    public void remove(long id) {
        this.transferRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.transferRepository.getSize();
    }


    private long doSaveAccounts(UserAccount senderAccount, UserAccount receiverAccount, double amount, String feeCode) throws InsufficientBalanceException, WrongBalanceTypeException {

        if (senderAccount.getAmount() < amount) {
            throw new InsufficientBalanceException("Your Account Amount little than you want to send");
        } else if (amount < 0 || amount == 0) {
            throw new WrongBalanceTypeException("Amount must be bigger than zero");
        } else {
            if (senderAccount.getUserId() == receiverAccount.getUserId() && senderAccount.getCurrencyId() == receiverAccount.getCurrencyId()) {
                //SEND EURO TO EURO SAME  CUSTOMERS ACCOUNTS
                double tempSenderAmount = senderAccount.getAmount();
                senderAccount.setAmount(tempSenderAmount - amount);

                double tempReceiverAmount = receiverAccount.getAmount();
                receiverAccount.setAmount(tempReceiverAmount + amount);

                userAccountService.update(senderAccount);
                userAccountService.update(receiverAccount);
            } else if ((senderAccount.getUserId() == receiverAccount.getUserId() && senderAccount.getCurrencyId() != receiverAccount.getCurrencyId()) || (senderAccount.getUserId() != receiverAccount.getUserId() && senderAccount.getCurrencyId() != receiverAccount.getCurrencyId())) {

                //SEND EURO TO TL SAME  CUSTOMERS ACCOUNTS CURRENCY EXCHANGE

                Currency senderCurrency = currencyService.getById(senderAccount.getCurrencyId());
                Currency receiverCurrency = currencyService.getById(receiverAccount.getCurrencyId());

                //EURO
                BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());

                //TL
                BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());


                double exchangeMoney = amount * RateConstant.get(senderCurrency.getShrtCode() + receiverCurrency.getShrtCode()).getRate();

                double feeMoney = exchangeMoney * FeeConstant.get(feeCode).getRate();


                double tempSenderAmount = senderAccount.getAmount();
                senderAccount.setAmount(tempSenderAmount - (amount));

                double tempReceiverAmount = receiverAccount.getAmount();
                receiverAccount.setAmount(tempReceiverAmount + exchangeMoney - feeMoney);


                double tempSenderBankAmount = senderBankAccount.getAmount();
                senderBankAccount.setAmount(tempSenderBankAmount + (amount));

                double tempReceiverBankAmount = receiverBankAccount.getAmount();
                receiverBankAccount.setAmount(tempReceiverBankAmount - exchangeMoney + feeMoney);

                bankAccountService.update(senderBankAccount);
                bankAccountService.update(receiverBankAccount);
                userAccountService.update(senderAccount);
                userAccountService.update(receiverAccount);
                return 0;//TODO: transactionId will send
            } else if (senderAccount.getUserId() != receiverAccount.getUserId() && senderAccount.getCurrencyId() == receiverAccount.getCurrencyId()) {
                //SEND EURO TO EURO SAME  CUSTOMERS ACCOUNTS
                double feeMoney = amount * FeeConstant.get(FeeConstant.INTERNAL.name()).getRate();
                double tempSenderAmount = senderAccount.getAmount();
                senderAccount.setAmount(tempSenderAmount - amount - feeMoney);

                double tempReceiverAmount = receiverAccount.getAmount();
                receiverAccount.setAmount(tempReceiverAmount + amount);

                BankAccount bankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());

                double tempBankAmount = bankAccount.getAmount();
                bankAccount.setAmount(tempBankAmount + feeMoney);

                userAccountService.update(senderAccount);
                userAccountService.update(receiverAccount);
                bankAccountService.update(bankAccount);

            }
        }
        return 0;
    }

    @Override
    public long transferAmongItsAccounts(long senderAccountId, long receiverAccountId, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getById(receiverAccountId);

        Currency senderCurrency = currencyService.getById(senderAccount.getCurrencyId());
        Currency receiverCurrency = currencyService.getById(receiverAccount.getCurrencyId());

        return doSaveAccounts(senderAccount, receiverAccount, amount, senderCurrency.getShrtCode() + receiverCurrency.getShrtCode());//TODO: transactionId will send
    }

    public long transferAmongInterNalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getByIban(iban);
        return doSaveAccounts(senderAccount, receiverAccount, amount, FeeConstant.INTERNAL.name());//TODO: transactionId will send
    }

    public long transferAmongInterNalBankAccounts(long senderAccountId, String accountNo, String bic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);

        Bank bank = bankService.getByBicCode(bic);

        UserAccount receiverAccount = userAccountService.getByAccountNoAndBankIdAndCurrencyId(accountNo, bank.getId(), senderAccount.getCurrencyId());

        return doSaveAccounts(senderAccount, receiverAccount, amount, FeeConstant.INTERNAL.name());//TODO: transactionId will send
    }

    public long transferAmongExterNalBankAccountsWithIban(long senderAccountId, String iban) {
        Account senderAccount = bankAccountService.getById(senderAccountId);
        return 0;
    }

    public long transferAmongExterNalBankAccounts(long senderAccountId, String accountno, String bic, double amount) {
        Account senderAccount = bankAccountService.getById(senderAccountId);
        return 0;
    }
}
