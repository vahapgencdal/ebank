package com.ebank.model.repository.impl;

import com.ebank.model.entity.TransactionType;
import com.ebank.model.repository.TransactionTypeRepository;
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
public class TransactionTypeRepositoryImpl extends BaseRepositoryImpl<TransactionType> implements TransactionTypeRepository {

    private List<TransactionType> transactionTypes;

    public TransactionTypeRepositoryImpl() {
        this.transactionTypes = new ArrayList<>();
        this.transactionTypes = this.createTransactionTypes();
    }

    public TransactionType getById(long id) {
        return this.transactionTypes.parallelStream().filter(transactionType -> transactionType.getId() == id).findAny().orElse(new TransactionType());
    }

    public List<TransactionType> getAll() {
        return this.transactionTypes;
    }

    @Override
    public TransactionType create(TransactionType transactionType) {
        transactionType.setId(getCurrentMaxId() + 1);
        this.transactionTypes.add(transactionType);
        return transactionType;
    }

    @Override
    public TransactionType update(TransactionType transactionType) {
        return this.getById(transactionType.getId());
    }

    @Override
    public void remove(long id) {
        TransactionType byId = this.getById(id);
        this.transactionTypes.remove(byId);
    }

    @Override
    public int getSize() {
        return this.transactionTypes.size();
    }

    private List<TransactionType> createTransactionTypes() {
        int numberOfTransactionTypes = 10;
        for (int i = 0; i < numberOfTransactionTypes; i++) {
            TransactionType transactionType = new TransactionType();
            transactionType.setId(i + 1);
            this.transactionTypes.add(transactionType);
        }
        return this.transactionTypes;
    }


    private long getCurrentMaxId() {
        Ordering<TransactionType> ordering = new Ordering<TransactionType>() {
            @Override
            public int compare(TransactionType left, TransactionType right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.transactionTypes).getId();
    }
}
