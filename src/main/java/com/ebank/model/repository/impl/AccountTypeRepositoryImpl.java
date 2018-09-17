package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.AccountType;
import com.ebank.model.repository.AccountTypeRepository;
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
public class AccountTypeRepositoryImpl extends BaseRepositoryImpl<AccountType> implements AccountTypeRepository {

    public AccountType getById(long id) {
        return DataSource.accountTypes.parallelStream().filter(accountType -> accountType.getId() == id).findAny().orElse(new AccountType());
    }

    public List<AccountType> getAll() {
        return DataSource.accountTypes;
    }

    @Override
    public AccountType create(AccountType accountType) {
        accountType.setId(getCurrentMaxId() + 1);
        DataSource.accountTypes.add(accountType);
        return accountType;
    }

    @Override
    public AccountType update(AccountType accountType) {
        AccountType acc = this.getById(accountType.getId());
        acc.setName(accountType.getName());
        acc.setDescr(accountType.getDescr());
        acc.setStatus(accountType.isStatus());
        acc.setUUser(accountType.getUUser());
        acc.setUDate(accountType.getUDate());
        return acc;
    }

    @Override
    public void remove(long id) {
        AccountType byId = this.getById(id);
        DataSource.accountTypes.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.accountTypes.size();
    }

    private List<AccountType> createAccountTypes() {
        int numberOfAccountTypes = 10;
        for (int i = 0; i < numberOfAccountTypes; i++) {
            AccountType accountType = new AccountType();
            accountType.setId(i + 1);
            accountType.setName("Account" + (i + 1));
            accountType.setDescr("Account" + (i + 1));
            DataSource.accountTypes.add(accountType);
        }
        return DataSource.accountTypes;
    }


    private long getCurrentMaxId() {
        if (DataSource.accountTypes.isEmpty()) return 0;
        Ordering<AccountType> ordering = new Ordering<AccountType>() {
            @Override
            public int compare(AccountType left, AccountType right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.accountTypes).getId();
    }
}
