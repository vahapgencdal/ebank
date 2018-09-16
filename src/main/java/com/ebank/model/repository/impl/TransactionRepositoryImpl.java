package com.ebank.model.repository.impl;

import com.ebank.model.entity.Transaction;
import com.ebank.model.repository.TransactionRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class TransactionRepositoryImpl extends BaseRepositoryImpl<Transaction> implements TransactionRepository {

    private List<Transaction> transactions;

    public TransactionRepositoryImpl() {
        this.transactions = new ArrayList<>();
        this.transactions = this.createTransactions();
    }

    public Transaction getById(long id) {
        return this.transactions.parallelStream().filter(transaction -> transaction.getId() == id).findAny().orElse(new Transaction());
    }

    public List<Transaction> getAll() {
        return this.transactions;
    }

    @Override
    public Transaction create(Transaction transaction) {
        transaction.setId(getCurrentMaxId() + 1);
        this.transactions.add(transaction);
        return transaction;
    }

    @Override
    public Transaction update(Transaction transaction) {
        return this.getById(transaction.getId());
    }

    @Override
    public void remove(long id) {
        Transaction byId = this.getById(id);
        this.transactions.remove(byId);
    }

    @Override
    public int getSize() {
        return this.transactions.size();
    }

    private List<Transaction> createTransactions() {
        int numberOfTransactions = 10;
        for (int i = 0; i < numberOfTransactions; i++) {
            Transaction transaction = new Transaction();
            transaction.setId(i + 1);
            this.transactions.add(transaction);
        }
        return this.transactions;
    }


    private long getCurrentMaxId() {
        Ordering<Transaction> ordering = new Ordering<Transaction>() {
            @Override
            public int compare(Transaction left, Transaction right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.transactions).getId();
    }
}
