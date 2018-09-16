package com.ebank.model.repository.impl;

import com.ebank.model.entity.Account;
import com.ebank.model.repository.AccountRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class AccountRepositoryImpl extends BaseRepositoryImpl<Account> implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryImpl() {
        this.accounts = new ArrayList<>();
        this.accounts = this.createAccounts();
    }

    public Account getById(long id) {
        return this.accounts.parallelStream().filter(account -> account.getId() == id).findAny().orElse(new Account());
    }

    public List<Account> getAll() {
        return this.accounts;
    }

    @Override
    public Account create(Account account) {
        account.setId(getCurrentMaxId() + 1);
        this.accounts.add(account);
        return account;
    }

    @Override
    public Account update(Account account) {
        Account byId = this.getById(account.getId());
        byId.setAccountNo(account.getAccountNo());
        byId.setIban(account.getIban());
        return byId;
    }

    @Override
    public void remove(long id) {
        Account byId = this.getById(id);
        this.accounts.remove(byId);
    }

    @Override
    public int getSize() {
        return this.accounts.size();
    }

    private List<Account> createAccounts() {
        int numberOfAccounts = 10;
        for (int i = 0; i < numberOfAccounts; i++) {
            Account account = new Account();
            account.setId(i + 1);
            account.setIban(UUID.randomUUID().toString());
            account.setAccountNo(UUID.randomUUID().toString());
            this.accounts.add(account);
        }
        return this.accounts;
    }


    private long getCurrentMaxId() {
        Ordering<Account> ordering = new Ordering<Account>() {
            @Override
            public int compare(Account left, Account right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.accounts).getId();
    }
}
