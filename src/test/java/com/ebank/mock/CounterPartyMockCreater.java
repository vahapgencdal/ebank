package com.ebank.mock;

import com.ebank.model.entity.CounterParty;

import java.util.UUID;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class CounterPartyMockCreater {
    public static CounterParty getTest(long currency) {
        CounterParty user = new CounterParty();
        user.setIban(UUID.randomUUID().toString());
        user.setAccountNo("CPM0001");
        user.setCurrency(currency);
        user.setEmail("vahap@email.com");
        user.setName("vahap other Account");
        user.setPhone("5421234567");
        user.setStatus(true);
        return user;
    }
}
