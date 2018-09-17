package com.ebank.mock;

import com.ebank.model.entity.AccountType;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class AccountTypeMockCreater {

    public static AccountType getTest() {
        AccountType accountType = new AccountType();
        accountType.setDescr("Test Account Type");
        accountType.setName("Test");
        accountType.setStatus(Boolean.TRUE);
        return accountType;
    }

    public static AccountType getDepositType() {
        AccountType accountType = new AccountType();
        accountType.setDescr("Deposit Account Type");
        accountType.setName("Deposit");
        accountType.setStatus(Boolean.TRUE);
        accountType.setCUser(1);
        return accountType;
    }

    public static AccountType getDrawingType() {
        AccountType accountType = new AccountType();
        accountType.setDescr("Drawing Account Type");
        accountType.setName("Drawing");
        accountType.setStatus(Boolean.TRUE);
        accountType.setCUser(1);
        return accountType;
    }

    public static AccountType updateAccount(AccountType acc, String name, String descr, boolean status) {
        acc.setName(name);
        acc.setDescr(descr);
        acc.setStatus(status);
        acc.setUUser(1);
        return acc;
    }

}
