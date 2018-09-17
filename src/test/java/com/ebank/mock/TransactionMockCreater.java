package com.ebank.mock;

import com.ebank.model.entity.Transaction;

import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class TransactionMockCreater {
    public static Transaction getTest() {
        Transaction transaction = new Transaction();
        transaction.setAmount(12);
        transaction.setFee(1);
        transaction.setReceiverAccountNo("123");
        transaction.setReceiverFullName("asd");
        transaction.setReceiverIban(UUID.randomUUID().toString());
        transaction.setSenderAccountNo("123");
        transaction.setSenderFullName("ad");
        transaction.setSenderIban(UUID.randomUUID().toString());
        transaction.setStatus("PENDING");
        transaction.setSenderBic("TEST-1");
        transaction.setReceiverBic("TEST-1");
        return transaction;
    }
}
