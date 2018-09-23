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

    EXTERNAL(0.8), INTERNAL(0.2);


    private final double rate;

    private static final Map<String, FeeConstant> lookup = new HashMap<>();

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

    FeeConstant(double rate) {
        this.rate = rate;
    }

}
