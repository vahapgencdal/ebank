package com.ebank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 18.09.2018
 * @description TODO: Class Description
 */
public enum UserEnum {
    VAHAP("VAHAP"), GUZEL("GUZEL"), ESMA("ESMA"), EMRE("EMRE"), BANK("BANK");

    private final String value;


    private static final Map<String, UserEnum> lookup = new HashMap<>();

    static {
        for (UserEnum d : UserEnum.values()) {
            lookup.put(d.name(), d);
        }
    }

    private UserEnum(String value) {
        this.value = value;
    }

    public static UserEnum get(String name) {
        return lookup.get(name);
    }

    public String getVal() {
        return value;
    }
}
