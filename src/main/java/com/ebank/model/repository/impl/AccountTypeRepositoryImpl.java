package com.ebank.model.repository.impl;

import com.ebank.model.entity.AccountType;
import com.ebank.model.repository.AccountTypeRepository;
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
public class AccountTypeRepositoryImpl extends BaseRepositoryImpl<AccountType> implements AccountTypeRepository {

    private List<AccountType> accountTypes;

    public AccountTypeRepositoryImpl() {
        this.accountTypes = new ArrayList<>();
        this.accountTypes = this.createAccountTypes();
    }

    public AccountType getById(long id) {
        return this.accountTypes.parallelStream().filter(accountType -> accountType.getId() == id).findAny().orElse(new AccountType());
    }

    public List<AccountType> getAll() {
        return this.accountTypes;
    }

    @Override
    public AccountType create(AccountType accountType) {
        accountType.setId(getCurrentMaxId() + 1);
        this.accountTypes.add(accountType);
        return accountType;
    }

    @Override
    public AccountType update(AccountType accountType) {
        AccountType byId = this.getById(accountType.getId());
        byId.setName(accountType.getName());
        byId.setDescr(accountType.getDescr());
        return byId;
    }

    @Override
    public void remove(long id) {
        AccountType byId = this.getById(id);
        this.accountTypes.remove(byId);
    }

    @Override
    public int getSize() {
        return this.accountTypes.size();
    }

    private List<AccountType> createAccountTypes() {
        int numberOfAccountTypes = 10;
        for (int i = 0; i < numberOfAccountTypes; i++) {
            AccountType accountType = new AccountType();
            accountType.setId(i + 1);
            accountType.setName("Account" + (i + 1));
            accountType.setDescr("Account" + (i + 1));
            this.accountTypes.add(accountType);
        }
        return this.accountTypes;
    }


    private long getCurrentMaxId() {
        Ordering<AccountType> ordering = new Ordering<AccountType>() {
            @Override
            public int compare(AccountType left, AccountType right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.accountTypes).getId();
    }
}
