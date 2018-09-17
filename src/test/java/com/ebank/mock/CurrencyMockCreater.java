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
        currency.setDescr("Euro");
        currency.setName("Euro");
        currency.setShrtCode("EUR");
        return currency;
    }

}
