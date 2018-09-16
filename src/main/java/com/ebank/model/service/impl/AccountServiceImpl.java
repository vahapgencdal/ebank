package com.ebank.model.service.impl;

import com.ebank.model.entity.Account;
import com.ebank.model.repository.AccountRepository;
import com.ebank.model.service.AccountService;
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
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.getAll();
    }

    @Override
    public Account getById(long id) {
        return this.accountRepository.getById(id);
    }

    @Override
    public Account create(Account account) {
        return this.accountRepository.create(account);
    }

    @Override
    public Account update(Account account) {
        return this.accountRepository.update(account);
    }

    @Override
    public void remove(long id) {
        this.accountRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.accountRepository.getSize();
    }
}
