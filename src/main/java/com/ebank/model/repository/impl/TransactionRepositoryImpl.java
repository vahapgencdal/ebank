package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.Transaction;
import com.ebank.model.repository.TransactionRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransactionRepositoryImpl extends BaseRepositoryImpl<Transaction> implements TransactionRepository {


    public Transaction getById(long id) {
        return DataSource.transactions.parallelStream().filter(transaction -> transaction.getId() == id).findAny().orElse(new Transaction());
    }

    public List<Transaction> getAll() {
        return DataSource.transactions;
    }

    @Override
    public Transaction create(Transaction transaction) {
        transaction.setId(getCurrentMaxId() + 1);
        DataSource.transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        Transaction byId = this.getById(transaction.getId());
        byId.setStatus(transaction.getStatus());
        return byId;
    }

    @Override
    public void remove(long id) {
        Transaction byId = this.getById(id);
        DataSource.transactions.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.transactions.size();
    }

    private List<Transaction> createTransactions() {
        int numberOfTransactions = 10;
        for (int i = 0; i < numberOfTransactions; i++) {
            Transaction transaction = new Transaction();
            transaction.setId(i + 1);
            DataSource.transactions.add(transaction);
        }
        return DataSource.transactions;
    }


    private long getCurrentMaxId() {
        if (DataSource.transactions.isEmpty()) return 0;
        Ordering<Transaction> ordering = new Ordering<Transaction>() {
            @Override
            public int compare(Transaction left, Transaction right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.transactions).getId();
    }
}
