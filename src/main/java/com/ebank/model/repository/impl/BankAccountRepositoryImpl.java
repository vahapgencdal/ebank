package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.BankAccount;
import com.ebank.model.repository.BankAccountRepository;
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
public class BankAccountRepositoryImpl extends BaseRepositoryImpl<BankAccount> implements BankAccountRepository {


    public BankAccount getById(long id) {

        return DataSource.bankAccounts.parallelStream().filter(account -> account.getId() == id).findAny().orElse(new BankAccount());
    }

    public List<BankAccount> getAll() {
        return DataSource.bankAccounts;
    }

    @Override
    public BankAccount create(BankAccount account) {
        account.setId(getCurrentMaxId() + 1);
        DataSource.bankAccounts.add(account);
        return account;
    }

    @Override
    public BankAccount update(BankAccount account) {
        BankAccount byId = this.getById(account.getId());
        byId.setTotalAmount(account.getTotalAmount());
        byId.setBlockedAmount(account.getBlockedAmount());
        byId.setName(account.getName());
        return byId;
    }

    @Override
    public void remove(long id) {
        BankAccount byId = this.getById(id);
        DataSource.bankAccounts.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.bankAccounts.size();
    }


    private long getCurrentMaxId() {
        if (DataSource.bankAccounts.isEmpty()) return 0;
        Ordering<BankAccount> ordering = new Ordering<BankAccount>() {
            @Override
            public int compare(BankAccount left, BankAccount right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.bankAccounts).getId();
    }


    @Override
    public BankAccount getByIban(String iban) {
        return DataSource.bankAccounts.parallelStream().filter(account -> account.getIban().equals(iban)).findAny().orElse(null);
    }

    @Override
    public BankAccount getByAccountNo(String accountNo, String bank) {
        return DataSource.bankAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBank().equals(bank)).findAny().orElse(null);
    }

    @Override
    public BankAccount getByCurrencyAndBank(String currency, String bank) {
        return DataSource.bankAccounts.parallelStream().filter(account -> account.getCurrency().equals(currency) && account.getBank().equals(bank)).findAny().orElse(null);
    }


}
