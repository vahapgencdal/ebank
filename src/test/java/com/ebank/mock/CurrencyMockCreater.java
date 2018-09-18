package com.ebank.mock;

import com.ebank.model.entity.Currency;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class CurrencyMockCreater {

    public static Currency getTest() {
        Currency currency = new Currency();
        currency.setDescr("Eusro");
        currency.setName("Eurso");
        currency.setShrtCode("EURs");
        return currency;
    }

    public static Currency create(String name, String shrtCode, String descr) {
        Currency currency = new Currency();
        currency.setDescr(descr);
        currency.setName(name);
        currency.setShrtCode(shrtCode);
        return currency;
    }

}
