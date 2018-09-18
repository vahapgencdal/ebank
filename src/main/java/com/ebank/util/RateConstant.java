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

    EURO_USD(2), EURO_TL(5), USD_TL(4), USD_EURO(0.5), TL_EURO(0.2), TL_USD(0.25);


    private final double rate;

    private static final Map<String, RateConstant> lookup = new HashMap<String, RateConstant>();

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

    private RateConstant(double rate) {
        this.rate = rate;
    }

}
