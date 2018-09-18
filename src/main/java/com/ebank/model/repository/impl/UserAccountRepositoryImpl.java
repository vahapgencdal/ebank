package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.repository.UserAccountRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class UserAccountRepositoryImpl extends BaseRepositoryImpl<UserAccount> implements UserAccountRepository {


    public UserAccount getById(long id) {

        return DataSource.userAccounts.parallelStream().filter(account -> account.getId() == id).findAny().orElse(new UserAccount());
    }

    public List<UserAccount> getAll() {
        return DataSource.userAccounts;
    }

    @Override
    public UserAccount create(UserAccount account) {
        account.setCDate(LocalDateTime.now());
        account.setCUser(1);
        account.setId(getCurrentMaxId() + 1);
        DataSource.userAccounts.add(account);
        return account;
    }

    @Override
    public UserAccount update(UserAccount account) {
        UserAccount byId = this.getById(account.getId());
        byId.setTotalAmount(account.getTotalAmount());
        byId.setBlockedAmount(account.getBlockedAmount());
        byId.setName(account.getName());
        byId.setUDate(LocalDateTime.now());
        byId.setUUser(1);
        byId.setStatus(account.isStatus());

        return byId;
    }

    @Override
    public void remove(long id) {
        UserAccount byId = this.getById(id);
        DataSource.userAccounts.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.userAccounts.size();
    }

    private long getCurrentMaxId() {
        if (DataSource.userAccounts.isEmpty()) return 0;
        Ordering<UserAccount> ordering = new Ordering<UserAccount>() {
            @Override
            public int compare(UserAccount left, UserAccount right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.userAccounts).getId();
    }

    @Override
    public UserAccount getByAccountTypeId(long accountTypeId) {
        return DataSource.userAccounts.parallelStream().filter(account -> account.getAccountType() == accountTypeId).findAny().orElse(null);
    }

    @Override
    public UserAccount getByIban(String iban) {
        return DataSource.userAccounts.parallelStream().filter(account -> account.getIban().equals(iban)).findAny().orElse(null);
    }

    @Override
    public UserAccount getByAccountNo(String accountNo, long bankId) {
        return DataSource.userAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBankId() == bankId).findAny().orElse(null);
    }

    @Override
    public UserAccount getByAccountNoAndBankIdAndCurrencyId(String accountNo, long bankId, long currencyId) {
        UserAccount userAccount = DataSource.userAccounts.parallelStream().filter(account -> account.getAccountNo().equals(accountNo) && account.getBankId() == bankId && account.getCurrencyId() == currencyId).findAny().orElse(null);

        if (userAccount == null)
            return DataSource.userAccounts.parallelStream().filter(account -> account.isDefault()).findAny().orElse(null);
        else return userAccount;
    }

}
