package com.ebank.model.service;

import com.ebank.model.entity.BankAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public interface BankAccountService extends BaseService<BankAccount> {


    BankAccount getByAccountNo(String accountNo, String bank);

    BankAccount getByCurrencyAndBank(String currency, String bank);

    BankAccount getById(long id);

    BankAccount getByIban(String iban);
}
