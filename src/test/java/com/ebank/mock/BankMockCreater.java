package com.ebank.mock;

import com.ebank.model.entity.Bank;

import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class BankMockCreater {
    public static Bank getTest() {
        Bank bank = new Bank();
        bank.setName("TEST-1");
        bank.setBic("TEST-1");
        bank.setStatus(true);
        bank.setAddressId(0);
        bank.setStatus(true);
        return bank;
    }


    public static Bank getIsBank(long addressId) {

        Bank bank = new Bank();
        bank.setBic(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4));
        bank.setStatus(Boolean.TRUE);
        bank.setAddressId(addressId);
        bank.setName("Is Bank");
        return bank;
    }

    public static Bank getGarantiBank(long addressId) {
        Bank bank = new Bank();
        bank.setBic(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4));
        bank.setStatus(Boolean.TRUE);
        bank.setAddressId(addressId);
        bank.setName("Garanti Bank");
        return bank;
    }
}
