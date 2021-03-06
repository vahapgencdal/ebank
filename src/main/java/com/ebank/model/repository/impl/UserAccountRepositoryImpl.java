package com.ebank.model.repository.impl;

import com.ebank.model.entity.UserAccount;
import com.ebank.model.repository.UserAccountRepository;
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
public class UserAccountRepositoryImpl extends BaseRepositoryImpl<UserAccount> implements UserAccountRepository {

    private List<UserAccount> userAccounts;

    public UserAccountRepositoryImpl() {
        userAccounts = new ArrayList<>();
    }

    public UserAccount getById(long id) {
        return this.userAccounts.parallelStream().filter(account -> account.getId() == id).findAny().orElse(new UserAccount());
    }

    public List<UserAccount> getAll() {
        return this.userAccounts;
    }

    @Override
    public UserAccount create(UserAccount account) {
        account.setId(getCurrentMaxId() + 1);
        this.userAccounts.add(account);
        return account;
    }

    @Override
    public UserAccount update(UserAccount account) {
        UserAccount byId = this.getById(account.getId());
        byId.setAmounts(account.getAmounts());
        byId.setName(account.getName());

        return byId;
    }

    @Override
    public void remove(long id) {
        UserAccount byId = this.getById(id);
        this.userAccounts.remove(byId);
    }

    @Override
    public int getSize() {
        return this.userAccounts.size();
    }

    private long getCurrentMaxId() {
        if (this.userAccounts.isEmpty()) return 0;
        Ordering<UserAccount> ordering = new Ordering<UserAccount>() {
            @Override
            public int compare(UserAccount left, UserAccount right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.userAccounts).getId();
    }


    @Override
    public UserAccount getByAccountNoAndBank(String accountNo, String bank) {
        return this.userAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBank().equals(bank)).findAny().orElse(null);
    }
}
