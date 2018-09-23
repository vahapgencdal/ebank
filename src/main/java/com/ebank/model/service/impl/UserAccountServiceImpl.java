package com.ebank.model.service.impl;

import com.ebank.model.entity.UserAccount;
import com.ebank.model.repository.UserAccountRepository;
import com.ebank.model.service.UserAccountService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Inject
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public List<UserAccount> getAll() {
        return this.userAccountRepository.getAll();
    }

    @Override
    public UserAccount getById(long id) {
        return this.userAccountRepository.getById(id);
    }

    @Override
    public UserAccount create(UserAccount account) {
        return this.userAccountRepository.create(account);
    }

    @Override
    public UserAccount update(UserAccount account) {
        return this.userAccountRepository.update(account);
    }

    @Override
    public void remove(long id) {
        this.userAccountRepository.remove(id);
    }

    @Override
    public UserAccount getByAccountNoAndBank(String accountNo, String bank) {
        return userAccountRepository.getByAccountNoAndBank(accountNo, bank);
    }
}
