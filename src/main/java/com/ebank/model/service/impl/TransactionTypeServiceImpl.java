package com.ebank.model.service.impl;

import com.ebank.model.entity.TransactionType;
import com.ebank.model.repository.TransactionTypeRepository;
import com.ebank.model.service.TransactionTypeService;
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
public class TransactionTypeServiceImpl implements TransactionTypeService {

    private final TransactionTypeRepository transactionTypeRepository;

    @Inject
    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public List<TransactionType> getAll() {
        return this.transactionTypeRepository.getAll();
    }

    @Override
    public TransactionType getById(long id) {
        return this.transactionTypeRepository.getById(id);
    }

    @Override
    public TransactionType create(TransactionType transactionType) {
        return this.transactionTypeRepository.create(transactionType);
    }

    @Override
    public TransactionType update(TransactionType transactionType) {
        return this.transactionTypeRepository.update(transactionType);
    }

    @Override
    public void remove(long id) {
        this.transactionTypeRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.transactionTypeRepository.getSize();
    }
}
