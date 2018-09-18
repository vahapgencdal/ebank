package com.ebank.datasource;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class DataSource {
    public static List<BankAccount> bankAccounts = new ArrayList<>();
    public static List<UserAccount> userAccounts = new ArrayList<>();
    public static List<Transaction> transactions = new ArrayList<>();


    public static void clearAllList() {
        bankAccounts.clear();
        userAccounts.clear();
    }
}
