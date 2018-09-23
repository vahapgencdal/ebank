package com.ebank.util;

import com.ebank.model.entity.Account;
import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.request.AccountRequest;
import com.ebank.model.request.TransactionRequest;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class TransactionCreater {

    private static Transaction createTransaction(Account senderAccount, Account receiverAccount, MonetaryAmount amount, MonetaryAmount feeAmount) {
        Transaction transaction = new Transaction();
        transaction.setSenderAccountId(senderAccount.getId());
        transaction.setSenderAccountNo(senderAccount.getAccountNo());
        transaction.setSenderBalance(senderAccount.getMonetary(amount.getCurrency().getCurrencyCode()));
        transaction.setSenderAccountName(senderAccount.getName());
        transaction.setReceiverAccountId(receiverAccount.getId());
        transaction.setReceiverBalance(receiverAccount.getMonetary(amount.getCurrency().getCurrencyCode()));
        transaction.setReceiverAccountNo(receiverAccount.getAccountNo());
        transaction.setReceiverAccountName(receiverAccount.getName());
        transaction.setAmount(amount);
        transaction.setFeeAmount(feeAmount);
        return transaction;
    }

    public static Transaction createBankTransaction(BankAccount senderAccount, BankAccount receiverAccount, MonetaryAmount amount, MonetaryAmount feeAmount) {
        Transaction transaction = createTransaction(senderAccount, receiverAccount, amount, feeAmount);
        transaction.setSenderBank(senderAccount.getBank());
        transaction.setReceiverBank(receiverAccount.getBank());
        return transaction;
    }

    public static Transaction createUserTransaction(UserAccount senderAccount, UserAccount receiverAccount, MonetaryAmount amount, MonetaryAmount feeAmount) {
        Transaction transaction = createTransaction(senderAccount, receiverAccount, amount, feeAmount);
        transaction.setSenderUser(senderAccount.getUser());
        transaction.setReceiverUser(receiverAccount.getUser());
        transaction.setSenderBank(senderAccount.getBank());
        transaction.setReceiverBank(receiverAccount.getBank());
        return transaction;
    }

    public static TransactionRequest createTransactionRequest(AccountRequest senderAccount, AccountRequest receiverAccount, MonetaryAmount amount, MonetaryAmount feeAmount) {
        TransactionRequest transaction = new TransactionRequest();
        transaction.setSenderAccountId(senderAccount.getId());
        transaction.setSenderAccountNo(senderAccount.getAccountNo());
        transaction.setSenderBalance(new BigDecimal(1000));
        transaction.setSenderCurrency("USD");
        transaction.setSenderAccountName(senderAccount.getName());
        transaction.setReceiverAccountId(receiverAccount.getId());
        transaction.setReceiverBalance(new BigDecimal(1000));
        transaction.setReceiverCurrency("USD");
        transaction.setReceiverAccountNo(receiverAccount.getAccountNo());
        transaction.setReceiverAccountName(receiverAccount.getName());
        transaction.setAmountBalance(new BigDecimal(11));
        transaction.setAmountCurrency("USD");
        transaction.setFeeAmount(new BigDecimal(11));
        transaction.setSenderBank("GARAN");
        transaction.setReceiverBank("GARAN");
        transaction.setReceiverUser("VAHAP");
        transaction.setSenderUser("VAHAP");
        return transaction;
    }

}
