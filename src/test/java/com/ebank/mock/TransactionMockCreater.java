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

    public static Transaction getTest(UserAccount senderAccount, UserAccount receiverAccount, double amount, double feeRate) {

        Transaction transaction = new Transaction();
        transaction.setSenderIban(senderAccount.getIban());
        transaction.setSenderAccountNo(senderAccount.getAccountNo());
        transaction.setSenderAccountAmount(senderAccount.getTotalAmount());
        transaction.setSenderAccountName(senderAccount.getName());
        transaction.setSenderBank(senderAccount.getBank());
        transaction.setSenderCurrency(senderAccount.getCurrency());
        transaction.setSenderDefaultAccount(senderAccount.isDefaultAccount());
        transaction.setSenderUser(senderAccount.getUser());

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
        return transaction;
    }
}
