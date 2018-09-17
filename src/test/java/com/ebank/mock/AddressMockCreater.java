package com.ebank.mock;

import com.ebank.model.entity.Address;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class AddressMockCreater {


    public static Address getTest() {
        Address address = new Address();
        address.setCity("istanbul");
        address.setCountry("Turkey");
        address.setState("Besiktas");
        address.setPostcode("34000");
        address.setStreet("Alakoc Sk. No:17/4");
        address.setStatus(true);
        return address;
    }
}
