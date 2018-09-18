package com.ebank.model.service.impl;

import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.exception.InsufficientBalanceException;
import com.ebank.model.exception.WrongBalanceTypeException;
import com.ebank.model.service.TransactionService;
import com.ebank.model.service.TransferService;
import com.ebank.model.service.UserAccountService;
import com.ebank.util.FeeConstant;
import com.ebank.util.TransactionStatus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransferServiceImpl implements TransferService {

    private final UserAccountService userAccountService;
    private final TransactionService transactionService;

    @Inject
    public TransferServiceImpl(UserAccountService userAccountService, TransactionService transactionService) {
        this.userAccountService = userAccountService;
        this.transactionService = transactionService;
    }

    private double getFee(UserAccount senderAccount, UserAccount receiverAccount) {
        if (senderAccount.getBank().equals(receiverAccount.getBank())) {
            if (senderAccount.getUser().equals(receiverAccount.getUser())) {
                if (senderAccount.getCurrency().equals(receiverAccount.getCurrency())) {
                    return 0;
                } else {
                    return FeeConstant.get(senderAccount.getCurrency() + "_" + receiverAccount.getCurrency()).getRate();
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
            transaction.setSenderAccountId(senderAccount.getId());
            transaction.setSenderIban(senderAccount.getIban());
            transaction.setSenderAccountNo(senderAccount.getAccountNo());
            transaction.setSenderAccountAmount(senderAccount.getTotalAmount());
            transaction.setSenderAccountName(senderAccount.getName());
            transaction.setSenderBank(senderAccount.getBank());
            transaction.setSenderCurrency(senderAccount.getCurrency());
            transaction.setSenderDefaultAccount(senderAccount.isDefaultAccount());
            transaction.setSenderUser(senderAccount.getUser());

            transaction.setReceiverAccountId(receiverAccount.getId());
            transaction.setReceiverIban(receiverAccount.getIban());
            transaction.setReceiverAccountNo(receiverAccount.getAccountNo());
            transaction.setReceiverAccountAmount(receiverAccount.getTotalAmount());
            transaction.setReceiverAccountName(receiverAccount.getName());
            transaction.setReceiverBank(receiverAccount.getBank());
            transaction.setReceiverCurrency(receiverAccount.getCurrency());
            transaction.setReceiverDefaultAccount(receiverAccount.isDefaultAccount());
            transaction.setReceiverUser(receiverAccount.getUser());

            transaction.setStatus(TransactionStatus.PENDING.toString());
            transaction.setAmount(amount);
            transaction.setFee(feeRate);
            Transaction t = transactionService.create(transaction);
            userAccountService.create(senderAccount);
            return t;
        }

    }

    @Override
    public Transaction transferAmongItsAccounts(long senderAccountId, long receiverAccountId, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getById(receiverAccountId);

        return createTransaction(senderAccount, receiverAccount, amount);

    }

    public Transaction transferAmongInternalBankAccountsWithIban(long senderAccountId, String iban, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverAccount = userAccountService.getByIban(iban);

        return createTransaction(senderAccount, receiverAccount, amount);

    }

    public Transaction transferAmongInternalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bank, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderAccount = userAccountService.getById(senderAccountId);

        UserAccount receiverAccount = userAccountService.getByAccountNoAndBankAndCurrency(accountNo, bank, senderAccount.getCurrency());

        return createTransaction(senderAccount, receiverAccount, amount);
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

    public Transaction transferAmongExterNalBankAccountsWithAccountNo(long senderAccountId, String accountNo, String bank, double amount) throws InsufficientBalanceException, WrongBalanceTypeException {
        UserAccount senderUserAccount = userAccountService.getById(senderAccountId);
        UserAccount receiverUserAccount = userAccountService.getByAccountNo(accountNo, bank);

        return createTransaction(senderUserAccount, receiverUserAccount, amount);
    }
}
