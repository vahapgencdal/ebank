package com.ebank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 18.09.2018
 * @description TODO: Class Description
 */
public enum BankEnum {
    GARAN("GARAN"), IS("IS");

    private final String value;


    private static final Map<String, BankEnum> lookup = new HashMap<>();

    static {
        for (BankEnum d : BankEnum.values()) {
            lookup.put(d.name(), d);
        }
    }

    private BankEnum(String value) {
        this.value = value;
    }

    public static BankEnum get(String name) {
        return lookup.get(name);
    }

    public String getVal() {
        return value;
    }
}
