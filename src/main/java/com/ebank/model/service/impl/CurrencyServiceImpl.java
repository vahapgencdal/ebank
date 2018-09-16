package com.ebank.model.service.impl;

import com.ebank.model.entity.Currency;
import com.ebank.model.repository.CurrencyRepository;
import com.ebank.model.service.CurrencyService;
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
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Inject
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getAll() {
        return this.currencyRepository.getAll();
    }

    @Override
    public Currency getById(long id) {
        return this.currencyRepository.getById(id);
    }

    @Override
    public Currency create(Currency currency) {
        return this.currencyRepository.create(currency);
    }

    @Override
    public Currency update(Currency currency) {
        return this.currencyRepository.update(currency);
    }

    @Override
    public void remove(long id) {
        this.currencyRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.currencyRepository.getSize();
    }
}
