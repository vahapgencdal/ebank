package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.CounterParty;
import com.ebank.model.repository.CounterPartyRepository;
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
public class CounterPartyRepositoryImpl extends BaseRepositoryImpl<CounterParty> implements CounterPartyRepository {


    public CounterParty getById(long id) {
        return DataSource.counterParties.parallelStream().filter(counterParty -> counterParty.getId() == id).findAny().orElse(new CounterParty());
    }

    public List<CounterParty> getAll() {
        return DataSource.counterParties;
    }

    @Override
    public CounterParty create(CounterParty counterParty) {
        counterParty.setId(getCurrentMaxId() + 1);
        DataSource.counterParties.add(counterParty);
        return counterParty;
    }

    @Override
    public CounterParty update(CounterParty counterParty) {
        CounterParty byId = this.getById(counterParty.getId());
        byId.setName(counterParty.getName());
        byId.setStatus(counterParty.isStatus());
        byId.setAccountNo(counterParty.getAccountNo());
        byId.setCurrency(counterParty.getCurrency());
        byId.setIban(counterParty.getIban());
        return byId;
    }

    @Override
    public void remove(long id) {
        CounterParty byId = this.getById(id);
        DataSource.counterParties.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.counterParties.size();
    }

    private List<CounterParty> createCounterParties() {
        int numberOfCounterPartys = 10;
        for (int i = 0; i < numberOfCounterPartys; i++) {
            CounterParty counterParty = new CounterParty();
            counterParty.setId(i + 1);
            counterParty.setName("Is" + (i + 1));
            DataSource.counterParties.add(counterParty);
        }
        return DataSource.counterParties;
    }


    private long getCurrentMaxId() {
        if (DataSource.counterParties.isEmpty()) return 0;
        Ordering<CounterParty> ordering = new Ordering<CounterParty>() {
            @Override
            public int compare(CounterParty left, CounterParty right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.counterParties).getId();
    }
}
