package com.ebank.model.service;

import com.ebank.model.entity.BankAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public interface BankAccountService extends BaseService<BankAccount> {


    BankAccount getByAccountNo(String accountNo, long bankId);

    BankAccount getByCurrencyIdAndBankId(long currencyId, long bankId);

    BankAccount getById(long id);

    BankAccount getByIban(String iban);
}
