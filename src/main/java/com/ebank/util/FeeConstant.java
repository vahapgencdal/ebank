package com.ebank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public enum FeeConstant {

    EURO_USD(1.2), EURO_TL(1.5), USD_TL(1.4), USD_EURO(1.2), TL_EURO(1.5), TL_USD(1.4), EXTERNAL(4.4), INTERNAL(2.0);


    private final double rate;

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<String, FeeConstant> lookup = new HashMap<String, FeeConstant>();

    static {
        for (FeeConstant d : FeeConstant.values()) {
            lookup.put(d.name(), d);
        }
    }


    public static FeeConstant get(String name) {
        return lookup.get(name);
    }

    public double getRate() {
        return rate;
    }

    private FeeConstant(double rate) {
        this.rate = rate;
    }

}
