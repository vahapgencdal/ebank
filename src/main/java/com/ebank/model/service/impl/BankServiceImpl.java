package com.ebank.model.service.impl;

import com.ebank.model.entity.Bank;
import com.ebank.model.repository.BankRepository;
import com.ebank.model.service.BankService;
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
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Inject
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<Bank> getAll() {
        return this.bankRepository.getAll();
    }

    @Override
    public Bank getById(long id) {
        return this.bankRepository.getById(id);
    }

    @Override
    public Bank create(Bank bank) {
        return this.bankRepository.create(bank);
    }

    @Override
    public Bank update(Bank bank) {
        return this.bankRepository.update(bank);
    }

    @Override
    public void remove(long id) {
        this.bankRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.bankRepository.getSize();
    }

    @Override
    public Bank getByBicCode(String bicCode) {
        return null;
    }
}
