package com.ebank.datasource;

import com.ebank.model.entity.*;

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
    public static List<AccountType> accountTypes = new ArrayList<>();
    public static List<Address> addresses = new ArrayList<>();
    public static List<Bank> banks = new ArrayList<>();
    public static List<CounterParty> counterParties = new ArrayList<>();
    public static List<Currency> currencies = new ArrayList<>();
    public static List<Transaction> transactions = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
}
