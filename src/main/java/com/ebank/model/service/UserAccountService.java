package com.ebank.model.service;

import com.ebank.model.entity.UserAccount;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public interface UserAccountService extends BaseService<UserAccount> {

    UserAccount getByAccountNoAndBank(String accountNo, String bankCode);

    UserAccount getById(long id);
}
