package com.ebank.model.repository;

import com.ebank.model.entity.BankAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface BankAccountRepository extends BaseRepository<BankAccount> {

    BankAccount getByIban(String iban);

    BankAccount getByAccountNo(String accountNo, long bankId);

    BankAccount getByCurrencyIdAndBankId(long currencyId, long bankId);
}
