package com.ebank.model.repository;

import com.ebank.model.entity.BankAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface BankAccountRepository extends BaseRepository<BankAccount> {

    BankAccount getByAccountNoAndBank(String accountNo, String bank);

    BankAccount getByBankCode(String bank);
}
