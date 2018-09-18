package com.ebank.model.repository;

import com.ebank.model.entity.UserAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface UserAccountRepository extends BaseRepository<UserAccount> {

    UserAccount getByAccountType(String accountType);

    UserAccount getByIban(String iban);

    UserAccount getByAccountNo(String accountNo, String bank);

    UserAccount getByAccountNoAndBankAndCurrency(String accountNo, String bank, String currency);
}
