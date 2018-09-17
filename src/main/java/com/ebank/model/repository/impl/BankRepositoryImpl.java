package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.Bank;
import com.ebank.model.repository.BankRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class BankRepositoryImpl extends BaseRepositoryImpl<Bank> implements BankRepository {



    public Bank getById(long id) {
        return DataSource.banks.parallelStream().filter(bank -> bank.getId() == id).findAny().orElse(new Bank());
    }

    public List<Bank> getAll() {
        return DataSource.banks;
    }

    @Override
    public Bank create(Bank bank) {
        bank.setId(getCurrentMaxId() + 1);
        DataSource.banks.add(bank);
        return bank;
    }

    @Override
    public Bank update(Bank bank) {
        Bank byId = this.getById(bank.getId());
        byId.setName(bank.getName());
        byId.setBic(bank.getBic());
        byId.setStatus(bank.isStatus());
        return byId;
    }

    @Override
    public void remove(long id) {
        Bank byId = this.getById(id);
        DataSource.banks.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.banks.size();
    }

    private List<Bank> createBanks() {
        int numberOfBanks = 10;
        for (int i = 0; i < numberOfBanks; i++) {
            Bank bank = new Bank();
            bank.setId(i + 1);
            bank.setName("Is" + (i + 1));
            DataSource.banks.add(bank);
        }
        return DataSource.banks;
    }


    private long getCurrentMaxId() {
        if (DataSource.banks.isEmpty()) return 0;
        Ordering<Bank> ordering = new Ordering<Bank>() {
            @Override
            public int compare(Bank left, Bank right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.banks).getId();
    }

    @Override
    public Bank getByBicCode(String bicCode) {
        return DataSource.banks.parallelStream().filter(bank -> bank.getBic().equals(bicCode)).findAny().orElse(null);
    }
}
