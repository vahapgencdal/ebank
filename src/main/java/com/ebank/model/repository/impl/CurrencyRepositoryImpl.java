package com.ebank.model.repository.impl;

import com.ebank.model.entity.Currency;
import com.ebank.model.repository.CurrencyRepository;
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
public class CurrencyRepositoryImpl extends BaseRepositoryImpl<Currency> implements CurrencyRepository {

    private List<Currency> currencies;

    public CurrencyRepositoryImpl() {
        this.currencies = new ArrayList<>();
        this.currencies = this.createCurrencies();
    }

    public Currency getById(long id) {
        return this.currencies.parallelStream().filter(currency -> currency.getId() == id).findAny().orElse(new Currency());
    }

    public List<Currency> getAll() {
        return this.currencies;
    }

    @Override
    public Currency create(Currency currency) {
        currency.setId(getCurrentMaxId() + 1);
        this.currencies.add(currency);
        return currency;
    }

    @Override
    public Currency update(Currency currency) {
        return this.getById(currency.getId());
    }

    @Override
    public void remove(long id) {
        Currency byId = this.getById(id);
        this.currencies.remove(byId);
    }

    @Override
    public int getSize() {
        return this.currencies.size();
    }

    private List<Currency> createCurrencies() {
        int numberOfCurrencys = 10;
        for (int i = 0; i < numberOfCurrencys; i++) {
            Currency currency = new Currency();
            currency.setId(i + 1);
            this.currencies.add(currency);
        }
        return this.currencies;
    }


    private long getCurrentMaxId() {
        Ordering<Currency> ordering = new Ordering<Currency>() {
            @Override
            public int compare(Currency left, Currency right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.currencies).getId();
    }
}
