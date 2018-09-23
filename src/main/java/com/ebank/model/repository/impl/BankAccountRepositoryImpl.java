package com.ebank.model.repository.impl;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.repository.BankAccountRepository;
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
public class BankAccountRepositoryImpl extends BaseRepositoryImpl<BankAccount> implements BankAccountRepository {

    private List<BankAccount> bankAccounts;

    public BankAccountRepositoryImpl() {
        this.bankAccounts = new ArrayList<>();
    }

    public BankAccount getById(long id) {

        return this.bankAccounts.parallelStream().filter(account -> account.getId() == id).findAny().orElse(new BankAccount());
    }

    public List<BankAccount> getAll() {
        return this.bankAccounts;
    }

    @Override
    public BankAccount create(BankAccount account) {
        account.setId(getCurrentMaxId() + 1);
        this.bankAccounts.add(account);
        return account;
    }

    @Override
    public BankAccount update(BankAccount account) {
        BankAccount byId = this.getById(account.getId());
        byId.setAmounts(account.getAmounts());
        byId.setName(account.getName());
        return byId;
    }

    @Override
    public void remove(long id) {
        BankAccount byId = this.getById(id);
        this.bankAccounts.remove(byId);
    }

    @Override
    public int getSize() {
        return this.bankAccounts.size();
    }


    private long getCurrentMaxId() {
        if (this.bankAccounts.isEmpty()) return 0;
        Ordering<BankAccount> ordering = new Ordering<BankAccount>() {
            @Override
            public int compare(BankAccount left, BankAccount right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.bankAccounts).getId();
    }


    @Override
    public BankAccount getByAccountNoAndBank(String accountNo, String bank) {
        return this.bankAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBank().equals(bank)).findAny().orElse(null);
    }

    @Override
    public BankAccount getByBankCode(String bank) {
        return this.bankAccounts.parallelStream().filter(account -> account.getBank().equals(bank)).findAny().orElse(null);
    }


}
