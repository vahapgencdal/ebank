package com.ebank.model.service.impl;

import com.ebank.model.entity.AccountType;
import com.ebank.model.repository.AccountTypeRepository;
import com.ebank.model.service.AccountTypeService;
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
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Inject
    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    @Override
    public List<AccountType> getAll() {
        return this.accountTypeRepository.getAll();
    }

    @Override
    public AccountType getById(long id) {
        return this.accountTypeRepository.getById(id);
    }

    @Override
    public AccountType create(AccountType accountType) {
        return this.accountTypeRepository.create(accountType);
    }

    @Override
    public AccountType update(AccountType accountType) {
        return this.accountTypeRepository.update(accountType);
    }

    @Override
    public void remove(long id) {
        this.accountTypeRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.accountTypeRepository.getSize();
    }
}
