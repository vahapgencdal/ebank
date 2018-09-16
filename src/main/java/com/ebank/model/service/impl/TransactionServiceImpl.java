package com.ebank.model.service.impl;

import com.ebank.model.entity.Transaction;
import com.ebank.model.repository.TransactionRepository;
import com.ebank.model.service.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Inject
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAll() {
        return this.transactionRepository.getAll();
    }

    @Override
    public Transaction getById(long id) {
        return this.transactionRepository.getById(id);
    }

    @Override
    public Transaction create(Transaction transaction) {
        return this.transactionRepository.create(transaction);
    }

    @Override
    public Transaction update(Transaction transaction) {
        return this.transactionRepository.update(transaction);
    }

    @Override
    public void remove(long id) {
        this.transactionRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.transactionRepository.getSize();
    }
}
