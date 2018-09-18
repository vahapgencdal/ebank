package com.ebank.model.service;

import com.ebank.model.entity.Transaction;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface TransactionService extends BaseService<Transaction> {

    Transaction getAnyPendingTransaction();

    Transaction complete(Transaction transaction);

}
