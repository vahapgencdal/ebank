package com.ebank.model.repository.impl;

import com.ebank.model.entity.CounterParty;
import com.ebank.model.repository.CounterPartyRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class CounterPartyRepositoryImpl extends BaseRepositoryImpl<CounterParty> implements CounterPartyRepository {

    private List<CounterParty> counterParties;

    public CounterPartyRepositoryImpl() {
        this.counterParties = new ArrayList<>();
        this.counterParties = this.createCounterParties();
    }

    public CounterParty getById(long id) {
        return this.counterParties.parallelStream().filter(counterParty -> counterParty.getId() == id).findAny().orElse(new CounterParty());
    }

    public List<CounterParty> getAll() {
        return this.counterParties;
    }

    @Override
    public CounterParty create(CounterParty counterParty) {
        counterParty.setId(getCurrentMaxId() + 1);
        this.counterParties.add(counterParty);
        return counterParty;
    }

    @Override
    public CounterParty update(CounterParty counterParty) {
        CounterParty byId = this.getById(counterParty.getId());
        byId.setName(counterParty.getName());
        return byId;
    }

    @Override
    public void remove(long id) {
        CounterParty byId = this.getById(id);
        this.counterParties.remove(byId);
    }

    @Override
    public int getSize() {
        return this.counterParties.size();
    }

    private List<CounterParty> createCounterParties() {
        int numberOfCounterPartys = 10;
        for (int i = 0; i < numberOfCounterPartys; i++) {
            CounterParty counterParty = new CounterParty();
            counterParty.setId(i + 1);
            counterParty.setName("Is" + (i + 1));
            this.counterParties.add(counterParty);
        }
        return this.counterParties;
    }


    private long getCurrentMaxId() {
        Ordering<CounterParty> ordering = new Ordering<CounterParty>() {
            @Override
            public int compare(CounterParty left, CounterParty right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.counterParties).getId();
    }
}
