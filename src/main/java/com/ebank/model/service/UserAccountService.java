package com.ebank.model.service;

import com.ebank.model.entity.UserAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public interface UserAccountService extends BaseService<UserAccount> {


    UserAccount getByAccountNo(String accountNo, long bankId);

    UserAccount getById(long id);

    UserAccount getByIban(String iban);

    UserAccount getByAccountNoAndBankIdAndCurrencyId(String accountNo, long id, long currencyId);

}
