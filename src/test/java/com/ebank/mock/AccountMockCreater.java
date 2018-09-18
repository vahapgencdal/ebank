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
    public static Account getBankAccount(String accountTypeId, String currencyId, String userId, String bankId) {
        Account account = new Account();
        account.setName("BankAccount");
        account.setAccountNo("111111");
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountTypeId);
        account.setTotalAmount(1000000000);
        account.setCurrency(currencyId);
        return account;
    }

    public static Account getTestAccountOne(String accountTypeId, String currencyId, String userId, String bankId) {
        Account account = new Account();
        account.setName("TEST-2");
        account.setAccountNo("222222");
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountTypeId);
        account.setTotalAmount(1000000000);
        account.setCurrency(currencyId);
        return account;
    }

    public static Account getTestAccountTwo(String accountTypeId, String currencyId, String userId, String bankId) {
        Account account = new Account();
        account.setName("TEST-3");
        account.setAccountNo("333333");
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountTypeId);
        account.setTotalAmount(1000000000);
        account.setCurrency(currencyId);
        return account;
    }


    public static BankAccount getIsBankOwnAccount(String currencyId, String accountType, String bankId, boolean isDefault) {

        BankAccount account = new BankAccount();

        account.setName("Is Bank Own Account");
        account.setCurrency(currencyId);
        account.setTotalAmount(1000000000);
        account.setBlockedAmount(0);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        account.setDefaultAccount(isDefault);
        return account;
    }

    public static BankAccount getGarantiOwnAccount(String currencyId, String accountType, String bankId, boolean isDefault) {

        BankAccount account = new BankAccount();
        account.setName("Garanti Own Account");
        account.setCurrency(currencyId);
        account.setTotalAmount(1000000000);
        account.setBlockedAmount(0);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        account.setDefaultAccount(isDefault);
        return account;
    }


    public static UserAccount getUserAccount(String userId, String currencyId, String accountType, String name, String bankId, boolean isDefault, long totalAmount) {

        UserAccount account = new UserAccount();
        account.setName(name);
        account.setUser(userId);
        account.setCurrency(currencyId);
        account.setTotalAmount(totalAmount);
        account.setBlockedAmount(0);
        account.setAccountNo(UUID.randomUUID().toString());
        account.setIban(UUID.randomUUID().toString());
        account.setType(accountType);
        account.setBank(bankId);
        account.setDefaultAccount(isDefault);
        return account;
    }

}
