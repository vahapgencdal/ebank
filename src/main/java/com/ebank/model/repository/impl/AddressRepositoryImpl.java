package com.ebank.model.repository.impl;

import com.ebank.model.entity.Address;
import com.ebank.model.repository.AddressRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class AddressRepositoryImpl extends BaseRepositoryImpl<Address> implements AddressRepository {

    private List<Address> addresses;

    public AddressRepositoryImpl() {
        this.addresses = new ArrayList<>();
        this.addresses = this.createAddresss();
    }

    public Address getById(long id) {
        return this.addresses.parallelStream().filter(address -> address.getId() == id).findAny().orElse(new Address());
    }

    public List<Address> getAll() {
        return this.addresses;
    }

    @Override
    public Address create(Address address) {
        address.setId(getCurrentMaxId() + 1);
        this.addresses.add(address);
        return address;
    }

    @Override
    public Address update(Address address) {
        Address byId = this.getById(address.getId());
        byId.setCity(address.getCity());
        byId.setCountry(address.getCountry());
        return byId;
    }

    @Override
    public void remove(long id) {
        Address byId = this.getById(id);
        this.addresses.remove(byId);
    }

    @Override
    public int getSize() {
        return this.addresses.size();
    }

    private List<Address> createAddresss() {
        int numberOfAddresss = 10;
        for (int i = 0; i < numberOfAddresss; i++) {
            Address address = new Address();
            address.setId(i + 1);
            address.setCity("Istanbul" + (i + 1));
            address.setCountry("Turkey" + (i + 1));
            this.addresses.add(address);
        }
        return this.addresses;
    }


    private long getCurrentMaxId() {
        Ordering<Address> ordering = new Ordering<Address>() {
            @Override
            public int compare(Address left, Address right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.addresses).getId();
    }
}
