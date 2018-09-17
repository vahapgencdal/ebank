package com.ebank.mock;

import com.ebank.model.entity.Account;
import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.UserAccount;

import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class AccountMockCreater {

    //Bank Account using for fee or another Incomes. when this change Case will change
    public static Account getBankAccount(long accountTypeId, long currencyId, long userId, long bankId) {
        Account account = new Account();
        account.setName("BankAccount");
        account.setAccountNo("111111");
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountTypeId);
        account.setAmount(1000000000);
        account.setCurrencyId(currencyId);
        account.setPublic(false);
        return account;
    }

    public static Account getTestAccountOne(long accountTypeId, long currencyId, long userId, long bankId) {
        Account account = new Account();
        account.setName("TEST-2");
        account.setAccountNo("222222");
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountTypeId);
        account.setAmount(1000000000);
        account.setCurrencyId(currencyId);
        account.setPublic(false);
        return account;
    }

    public static Account getTestAccountTwo(long accountTypeId, long currencyId, long userId, long bankId) {
        Account account = new Account();
        account.setName("TEST-3");
        account.setAccountNo("333333");
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountTypeId);
        account.setAmount(1000000000);
        account.setCurrencyId(currencyId);
        account.setPublic(false);
        return account;
    }


    private BankAccount getIsBankOwnAccount(long currencyId, long accountType, long bankId, boolean isDefault) {

        BankAccount account = new BankAccount();

        account.setName("Is Bank Own Account");
        account.setPublic(Boolean.FALSE);
        account.setCurrencyId(currencyId);
        account.setAmount(1000000000);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountType);
        account.setStatus(Boolean.TRUE);
        account.setBankId(bankId);
        account.setDefault(isDefault);
        return account;
    }

    private BankAccount getGarantiOwnAccount(long userId, long currencyId, long accountType, long bankId, boolean isDefault) {

        BankAccount account = new BankAccount();
        account.setName("Garanti Own Account");
        account.setPublic(Boolean.FALSE);
        account.setCurrencyId(currencyId);
        account.setAmount(1000000000);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountType);
        account.setStatus(Boolean.TRUE);
        account.setBankId(bankId);
        account.setDefault(isDefault);
        return account;
    }


    private UserAccount getUserAccount(long userId, long currencyId, long accountType, String name, long bankId, boolean isDefault) {

        UserAccount account = new UserAccount();
        account.setName(name);
        account.setPublic(Boolean.FALSE);
        account.setUserId(userId);
        account.setCurrencyId(currencyId);
        account.setAmount(0);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setAccountType(accountType);
        account.setStatus(Boolean.TRUE);
        account.setBankId(bankId);
        account.setDefault(isDefault);
        return account;
    }

}
