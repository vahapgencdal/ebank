package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.BankAccount;
import com.ebank.model.repository.BankAccountRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.List;
import java.util.UUID;

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
        byId.setAccountNo(account.getAccountNo());
        byId.setIban(account.getIban());
        byId.setStatus(account.isStatus());
        byId.setAmount(account.getAmount());
        byId.setCurrencyId(account.getCurrencyId());
        byId.setPublic(account.isPublic());
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

    private List<BankAccount> createAccounts() {
        int numberOfAccounts = 10;
        for (int i = 0; i < numberOfAccounts; i++) {
            BankAccount account = new BankAccount();
            account.setId(i + 1);
            account.setIban(UUID.randomUUID().toString());
            account.setAccountNo(UUID.randomUUID().toString());
            DataSource.bankAccounts.add(account);
        }
        return DataSource.bankAccounts;
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
    public BankAccount getByAccountNo(String accountNo, long bankId) {
        return DataSource.bankAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBankId() == bankId).findAny().orElse(null);
    }

    @Override
    public BankAccount getByCurrencyIdAndBankId(long currencyId, long bankId) {
        return DataSource.bankAccounts.parallelStream().filter(account -> account.getCurrencyId() == currencyId && account.getBankId() == bankId).findAny().orElse(null);
    }


}
