package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.Currency;
import com.ebank.model.repository.CurrencyRepository;
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
public class CurrencyRepositoryImpl extends BaseRepositoryImpl<Currency> implements CurrencyRepository {

    public Currency getById(long id) {
        return DataSource.currencies.parallelStream().filter(currency -> currency.getId() == id).findAny().orElse(new Currency());
    }

    public List<Currency> getAll() {
        return DataSource.currencies;
    }

    @Override
    public Currency create(Currency currency) {
        currency.setId(getCurrentMaxId() + 1);
        DataSource.currencies.add(currency);
        return currency;
    }

    @Override
    public Currency update(Currency currency) {
        Currency byId = this.getById(currency.getId());
        byId.setShrtCode(currency.getShrtCode());
        byId.setName(currency.getName());
        byId.setDescr(currency.getDescr());
        byId.setStatus(currency.isStatus());
        return byId;
    }

    @Override
    public void remove(long id) {
        Currency byId = this.getById(id);
        DataSource.currencies.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.currencies.size();
    }

    private List<Currency> createCurrencies() {
        int numberOfCurrencys = 10;
        for (int i = 0; i < numberOfCurrencys; i++) {
            Currency currency = new Currency();
            currency.setId(i + 1);
            DataSource.currencies.add(currency);
        }
        return DataSource.currencies;
    }


    private long getCurrentMaxId() {
        if (DataSource.currencies.isEmpty()) return 0;
        Ordering<Currency> ordering = new Ordering<Currency>() {
            @Override
            public int compare(Currency left, Currency right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.currencies).getId();
    }
}
