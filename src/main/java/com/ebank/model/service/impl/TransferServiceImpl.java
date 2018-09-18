package com.ebank.model.service.impl;

import com.ebank.model.entity.Bank;
import com.ebank.model.entity.Currency;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.request.InternalTransferWithAccountNoTransferRequest;
import com.ebank.model.service.*;
import com.ebank.util.FeeConstant;
import com.ebank.util.TransactionStatus;
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

    private final UserAccountService userAccountService;
    private final BankService bankService;
    private final CurrencyService currencyService;
    private final TransactionService transactionService;

    @Inject
    public TransferServiceImpl(BankService bankService, UserAccountService userAccountService, CurrencyService currencyService, TransactionService transactionService) {
        this.userAccountService = userAccountService;
        this.currencyService = currencyService;
        this.bankService = bankService;
        this.transactionService = transactionService;
    }

    private double getFee(UserAccount senderAccount, UserAccount receiverAccount) {
        if (senderAccount.getBankId() == receiverAccount.getBankId()) {
            if (senderAccount.getUserId() == receiverAccount.getUserId()) {
                if (senderAccount.getCurrencyId() == receiverAccount.getCurrencyId()) {
                    return 0;
                } else {
                    Currency senderCurrency = currencyService.getById(senderAccount.getCurrencyId());
                    Currency receiverCurrency = currencyService.getById(receiverAccount.getCurrencyId());
                    return FeeConstant.get(senderCurrency.getShrtCode() + "_" + receiverCurrency.getShrtCode()).getRate();
                }

            } else {
                return FeeConstant.get(FeeConstant.INTERNAL.name()).getRate();
            }
        } else {
            return FeeConstant.get(FeeConstant.EXTERNAL.name()).getRate();
        }
    }


    private Transaction createTransaction(UserAccount senderAccount, UserAccount receiverAccount, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {

        double feeRate = getFee(senderAccount, receiverAccount);
        double senderBlockedAmount = senderAccount.getBlockedAmount() + amount + feeRate * amount;

        if (senderAccount.getTotalAmount() < amount + feeRate * amount) {
            throw new InsufficientBalanceException("Your Account Amount little than you want to send and fee");
        } else if (amount < 0 || amount == 0) {
            throw new WrongBalanceTypeException("Amount must be bigger than zero");
        } else {

            senderAccount.setBlockedAmount(senderBlockedAmount);
            double totalAmount = senderAccount.getTotalAmount() - (amount + feeRate * amount);
            senderAccount.setTotalAmount(totalAmount);

            Transaction transaction = new Transaction();
            transaction.setSenderIban(senderAccount.getIban());
            transaction.setSenderAccountNo(senderAccount.getAccountNo());
            transaction.setSenderAccountAmount(senderAccount.getTotalAmount());
            transaction.setSenderAccountName(senderAccount.getName());
            transaction.setSenderBankId(senderAccount.getBankId());
            transaction.setSenderCurrencyId(senderAccount.getCurrencyId());
            transaction.setSenderIsDefault(senderAccount.isDefault());
            transaction.setSenderUserId(senderAccount.getUserId());

            transaction.setReceiverIban(receiverAccount.getIban());
            transaction.setReceiverAccountNo(receiverAccount.getAccountNo());
            transaction.setReceiverAccountAmount(receiverAccount.getTotalAmount());
            transaction.setReceiverAccountName(receiverAccount.getName());
            transaction.setReceiverBankId(receiverAccount.getBankId());
            transaction.setReceiverCurrencyId(receiverAccount.getCurrencyId());
            transaction.setReceiverIsDefault(receiverAccount.isDefault());
            transaction.setReceiverUserId(receiverAccount.getUserId());

            transaction.setStatus(TransactionStatus.PENDING.toString());
            transaction.setAmount(amount);
            transaction.setFee(feeRate);

            Transaction t = transactionService.create(transaction);
            userAccountService.update(senderAccount);
            return t;
        }

    }


    /**
     * private long doSaveAccounts(UserAccount senderAccount, UserAccount receiverAccount, double amount, String feeCode) throws InsufficientBalanceException, WrongBalanceTypeException {
     * <p>
     * if (senderAccount.getAmount() < amount) {
     * throw new InsufficientBalanceException("Your Account Amount little than you want to send");
     * } else if (amount < 0 || amount == 0) {
     * throw new WrongBalanceTypeException("Amount must be bigger than zero");
     * } else {
     * if (senderAccount.getUserId() == receiverAccount.getUserId() && senderAccount.getCurrencyId() == receiverAccount.getCurrencyId()) {
     * //SEND EURO TO EURO SAME  CUSTOMERS ACCOUNTS
     * double tempSenderAmount = senderAccount.getAmount();
     * senderAccount.setAmount(tempSenderAmount - amount);
     * <p>
     * double tempReceiverAmount = receiverAccount.getAmount();
     * receiverAccount.setAmount(tempReceiverAmount + amount);
     * <p>
     * userAccountService.update(senderAccount);
     * userAccountService.update(receiverAccount);
     * } else if ((senderAccount.getUserId() == receiverAccount.getUserId() && senderAccount.getCurrencyId() != receiverAccount.getCurrencyId()) || (senderAccount.getUserId() != receiverAccount.getUserId() && senderAccount.getCurrencyId() != receiverAccount.getCurrencyId())) {
     * <p>
     * //SEND EURO TO TL SAME  CUSTOMERS ACCOUNTS CURRENCY EXCHANGE
     * <p>
     * Currency senderCurrency = currencyService.getById(senderAccount.getCurrencyId());
     * Currency receiverCurrency = currencyService.getById(receiverAccount.getCurrencyId());
     * <p>
     * //EURO
     * BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());
     * <p>
     * //TL
     * BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());
     * <p>
     * //specify fee
     * double feeAmount = amount * FeeConstant.get(feeCode).getRate();
     * <p>
     * //extract fee from amount and exhange amount
     * double exchangeAmount = (amount-feeAmount) * RateConstant.get(senderCurrency.getShrtCode() +"_"+ receiverCurrency.getShrtCode()).getRate();
     * <p>
     * //add exhange to user exhanged money account
     * double tempReceiverAmount = receiverAccount.getAmount();
     * receiverAccount.setAmount(tempReceiverAmount + exchangeAmount);
     * <p>
     * <p>
     * //extract exchange to bank money account
     * double tempReceiverBankAmount = receiverBankAccount.getAmount();
     * receiverBankAccount.setAmount(tempReceiverBankAmount-exchangeAmount);
     * <p>
     * //extract orjinal amount from sender Account
     * double tempSenderAmount = senderAccount.getAmount();
     * senderAccount.setAmount(tempSenderAmount - (amount));
     * <p>
     * //add orjinal amount to bankAccount
     * double tempSenderBankAmount = senderBankAccount.getAmount();
     * senderBankAccount.setAmount(tempSenderBankAmount + amount);
     * <p>
     * bankAccountService.update(senderBankAccount);
     * bankAccountService.update(receiverBankAccount);
     * userAccountService.update(senderAccount);
     * userAccountService.update(receiverAccount);
     * return 0;//TODO: transactionId will send
     * } else if (senderAccount.getUserId() != receiverAccount.getUserId() && senderAccount.getCurrencyId() == receiverAccount.getCurrencyId()) {
     * //SEND EURO TO EURO SAME  CUSTOMERS ACCOUNTS
     * double feeMoney = amount * FeeConstant.get(FeeConstant.INTERNAL.name()).getRate();
     * double tempSenderAmount = senderAccount.getAmount();
     * senderAccount.setAmount(tempSenderAmount - amount - feeMoney);
     * <p>
     * double tempReceiverAmount = receiverAccount.getAmount();
     * receiverAccount.setAmount(tempReceiverAmount + amount);
     * <p>
     * BankAccount bankAccount = bankAccountService.getByCurrencyIdAndBankId(senderAccount.getCurrencyId(), senderAccount.getBankId());
     * <p>
     * double tempBankAmount = bankAccount.getAmount();
     * bankAccount.setAmount(tempBankAmount + feeMoney);
     * <p>
     * userAccountService.update(senderAccount);
     * userAccountService.update(receiverAccount);
     * bankAccountService.update(bankAccount);
     * <p>
     * }
     * }
     * return 0;
     * }
     */

    @Override
    public Transaction transferAmongItsAccounts(long senderAccountId, long receiverAccountId, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getById(receiverAccountId);

        return createTransaction(senderAccount, receiverAccount, amount);

        //return doSaveAccounts(senderAccount, receiverAccount, amount, senderCurrency.getShrtCode()+"_"+ receiverCurrency.getShrtCode());//TODO: transactionId will send
    }

    public Transaction transferAmongInternalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getByIban(iban);

        return createTransaction(senderAccount, receiverAccount, amount);

        //return doSaveAccounts(senderAccount, receiverAccount, amount, FeeConstant.INTERNAL.name());//TODO: transactionId will send
    }

    public Transaction transferAmongInternalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);

        Bank bank = bankService.getByBicCode(bic);

        UserAccount receiverAccount = userAccountService.getByAccountNoAndBankIdAndCurrencyId(accountNo, bank.getId(), senderAccount.getCurrencyId());


        return createTransaction(senderAccount, receiverAccount, amount);
        //return doSaveAccounts(senderAccount, receiverAccount, amount, FeeConstant.INTERNAL.name());//TODO: transactionId will send
    }

    public Transaction transferAmongExterNalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {

        UserAccount senderUserAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverUserAccount = userAccountService.getByIban(iban);


        /*
        if(senderUserAccount.getCurrencyId()==receiverUserAccount.getCurrencyId()){
            Bank senderBank=bankService.getById(senderUserAccount.getBankId());
            Bank receiverBank=bankService.getById(receiverUserAccount.getBankId());

            BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderUserAccount.getCurrencyId(),senderBank.getId());
            BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(receiverUserAccount.getCurrencyId(),receiverBank.getId());


            double senderUserAccountTemp= senderBankAccount.getAmount();

            double fee=FeeConstant.get(FeeConstant.EXTERNAL.name()).getRate()*amount;

            senderUserAccount.setAmount(senderUserAccountTemp-amount-fee);

            double senderBankAccountTemp=senderBankAccount.getAmount();
            senderBankAccount.setAmount(senderBankAccountTemp-amount+fee);

            double receiverUserAccountTemp=receiverUserAccount.getAmount();
            receiverUserAccount.setAmount(receiverUserAccountTemp+amount);


            double receiverBankAccountTemp=receiverBankAccount.getAmount();
            receiverBankAccount.setAmount(receiverBankAccountTemp-amount);
        }else{
            //SEND EURO TO TL DIFFERENT  CUSTOMERS ACCOUNTS CURRENCY EXCHANGE
            Currency senderCurrency = currencyService.getById(senderUserAccount.getCurrencyId());
            Currency receiverCurrency = currencyService.getById(receiverUserAccount.getCurrencyId());

            //EURO
            BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderUserAccount.getCurrencyId(), senderUserAccount.getBankId());

            //TL
            BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(receiverUserAccount.getCurrencyId(), receiverUserAccount.getBankId());

            //specify fee
            double feeAmount = amount * FeeConstant.get(senderCurrency.getShrtCode()+"_"+receiverCurrency.getShrtCode()).getRate();

            //extract fee from amount and exhange amount
            double exchangeAmount = (amount-feeAmount) * RateConstant.get(senderCurrency.getShrtCode() +"_"+ receiverCurrency.getShrtCode()).getRate();

            //add exhange to user exhanged money account
            double tempReceiverAmount = receiverUserAccount.getAmount();
            receiverUserAccount.setAmount(tempReceiverAmount + exchangeAmount);


            //extract exchange to bank money account
            double tempReceiverBankAmount = receiverBankAccount.getAmount();
            receiverBankAccount.setAmount(tempReceiverBankAmount-exchangeAmount);

            //extract orjinal amount from sender Account
            double tempSenderAmount = senderUserAccount.getAmount();
            senderUserAccount.setAmount(tempSenderAmount - (amount));

            //add orjinal amount to bankAccount
            double tempSenderBankAmount = senderBankAccount.getAmount();
            senderBankAccount.setAmount(tempSenderBankAmount + amount);

            bankAccountService.update(senderBankAccount);
            bankAccountService.update(receiverBankAccount);
            userAccountService.update(senderUserAccount);
            userAccountService.update(receiverUserAccount);
        }
        */
        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }

    public Transaction transferAmongExterNalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bic, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getById(senderAccountId);
        Bank receiverBank = bankService.getByBicCode(bic);
        UserAccount receiverUserAccount = userAccountService.getByAccountNo(accountNo, receiverBank.getId());

        /*if(senderUserAccount.getCurrencyId()==receiverUserAccount.getCurrencyId()){
            Bank senderBank=bankService.getById(senderUserAccount.getBankId());
            Bank receiverBank=bankService.getById(receiverUserAccount.getBankId());

            BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderUserAccount.getCurrencyId(),senderBank.getId());
            BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(receiverUserAccount.getCurrencyId(),receiverBank.getId());


            double senderUserAccountTemp= senderBankAccount.getAmount();

            double fee=FeeConstant.get(FeeConstant.EXTERNAL.name()).getRate()*amount;

            senderUserAccount.setAmount(senderUserAccountTemp-amount-fee);

            double senderBankAccountTemp=senderBankAccount.getAmount();
            senderBankAccount.setAmount(senderBankAccountTemp-amount+fee);

            double receiverUserAccountTemp=receiverUserAccount.getAmount();
            receiverUserAccount.setAmount(receiverUserAccountTemp+amount);


            double receiverBankAccountTemp=receiverBankAccount.getAmount();
            receiverBankAccount.setAmount(receiverBankAccountTemp-amount);
        }else{
            //SEND EURO TO TL DIFFERENT  CUSTOMERS ACCOUNTS CURRENCY EXCHANGE
            Currency senderCurrency = currencyService.getById(senderUserAccount.getCurrencyId());
            Currency receiverCurrency = currencyService.getById(receiverUserAccount.getCurrencyId());

            //EURO
            BankAccount senderBankAccount = bankAccountService.getByCurrencyIdAndBankId(senderUserAccount.getCurrencyId(), senderUserAccount.getBankId());

            //TL
            BankAccount receiverBankAccount = bankAccountService.getByCurrencyIdAndBankId(receiverUserAccount.getCurrencyId(), receiverUserAccount.getBankId());

            //specify fee
            double feeAmount = amount * FeeConstant.get(senderCurrency.getShrtCode()+"_"+receiverCurrency.getShrtCode()).getRate();

            //extract fee from amount and exhange amount
            double exchangeAmount = (amount-feeAmount) * RateConstant.get(senderCurrency.getShrtCode() +"_"+ receiverCurrency.getShrtCode()).getRate();

            //add exhange to user exhanged money account
            double tempReceiverAmount = receiverUserAccount.getAmount();
            receiverUserAccount.setAmount(tempReceiverAmount + exchangeAmount);


            //extract exchange to bank money account
            double tempReceiverBankAmount = receiverBankAccount.getAmount();
            receiverBankAccount.setAmount(tempReceiverBankAmount-exchangeAmount);

            //extract orjinal amount from sender Account
            double tempSenderAmount = senderUserAccount.getAmount();
            senderUserAccount.setAmount(tempSenderAmount - (amount));

            //add orjinal amount to bankAccount
            double tempSenderBankAmount = senderBankAccount.getAmount();
            senderBankAccount.setAmount(tempSenderBankAmount + amount);

            bankAccountService.update(senderBankAccount);
            bankAccountService.update(receiverBankAccount);
            userAccountService.update(senderUserAccount);
            userAccountService.update(receiverUserAccount);
        }
     */
        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }

    @Override
    public InternalTransferWithAccountNoTransferRequest create(InternalTransferWithAccountNoTransferRequest internalTransferWithAccountNoTransferRequest) {
        return null;
    }

    @Override
    public InternalTransferWithAccountNoTransferRequest update(InternalTransferWithAccountNoTransferRequest internalTransferWithAccountNoTransferRequest) {
        return null;
    }

    @Override
    public List<InternalTransferWithAccountNoTransferRequest> getAll() {
        return null;
    }

    @Override
    public InternalTransferWithAccountNoTransferRequest getById(long id) {
        return null;
    }

    @Override
    public void remove(long id) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
