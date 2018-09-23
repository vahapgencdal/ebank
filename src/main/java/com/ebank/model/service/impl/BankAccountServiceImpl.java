package com.ebank.model.service.impl;

import com.ebank.model.entity.BankAccount;
import com.ebank.model.repository.BankAccountRepository;
import com.ebank.model.service.BankAccountService;
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
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Inject
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<BankAccount> getAll() {
        return this.bankAccountRepository.getAll();
    }

    @Override
    public BankAccount getById(long id) {
        return this.bankAccountRepository.getById(id);
    }

    @Override
    public BankAccount create(BankAccount account) {
        return this.bankAccountRepository.create(account);
    }

    @Override
    public BankAccount update(BankAccount account) {
        return this.bankAccountRepository.update(account);
    }

    @Override
    public void remove(long id) {
        this.bankAccountRepository.remove(id);
    }

    @Override
    public BankAccount getByAccountNoAndBank(String accountNo, String bank) {
        return bankAccountRepository.getByAccountNoAndBank(accountNo, bank);
    }

    @Override
    public BankAccount getByBankCode(String bankCode) {
        return bankAccountRepository.getByBankCode(bankCode);
    }
}
