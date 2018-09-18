package com.ebank.mock;

import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.util.TransactionStatus;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class TransactionMockCreater {

    public Transaction getTest(UserAccount senderAccount, UserAccount receiverAccount, double amount, double feeRate) {

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
        return transaction;
    }
}
