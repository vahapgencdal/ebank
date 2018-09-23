package com.ebank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public enum RateConstant {

    EUR_USD(2), EUR_TL(5), USD_TL(4), USD_EUR(0.5), TL_EUR(0.2), TL_USD(0.25);

    private final double rate;

    private static final Map<String, RateConstant> lookup = new HashMap<>();

    static {
        for (RateConstant d : RateConstant.values()) {
            lookup.put(d.name(), d);
        }
    }


    public static RateConstant get(String name) {
        return lookup.get(name);
    }

    public double getRate() {
        return rate;
    }

    RateConstant(double rate) {
        this.rate = rate;
    }

}
