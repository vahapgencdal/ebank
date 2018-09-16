package com.ebank.model.service.impl;

import com.ebank.model.entity.CounterParty;
import com.ebank.model.repository.CounterPartyRepository;
import com.ebank.model.service.CounterPartyService;
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
public class CounterPartyServiceImpl implements CounterPartyService {

    private final CounterPartyRepository counterPartyRepository;

    @Inject
    public CounterPartyServiceImpl(CounterPartyRepository counterPartyRepository) {
        this.counterPartyRepository = counterPartyRepository;
    }

    @Override
    public List<CounterParty> getAll() {
        return this.counterPartyRepository.getAll();
    }

    @Override
    public CounterParty getById(long id) {
        return this.counterPartyRepository.getById(id);
    }

    @Override
    public CounterParty create(CounterParty counterParty) {
        return this.counterPartyRepository.create(counterParty);
    }

    @Override
    public CounterParty update(CounterParty counterParty) {
        return this.counterPartyRepository.update(counterParty);
    }

    @Override
    public void remove(long id) {
        this.counterPartyRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.counterPartyRepository.getSize();
    }
}
