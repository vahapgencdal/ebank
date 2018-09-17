package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.Address;
import com.ebank.model.repository.AddressRepository;
import com.google.common.collect.Ordering;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class AddressRepositoryImpl extends BaseRepositoryImpl<Address> implements AddressRepository {


    public Address getById(long id) {
        return DataSource.addresses.parallelStream().filter(address -> address.getId() == id).findAny().orElse(new Address());
    }

    public List<Address> getAll() {
        return DataSource.addresses;
    }

    @Override
    public Address create(Address address) {
        address.setId(getCurrentMaxId() + 1);
        DataSource.addresses.add(address);
        return address;
    }

    @Override
    public Address update(Address address) {
        Address byId = this.getById(address.getId());
        byId.setCity(address.getCity());
        byId.setCountry(address.getCountry());
        byId.setStreet(address.getStreet());
        byId.setPostcode(address.getPostcode());
        byId.setStatus(address.isStatus());
        byId.setState(address.getState());
        return byId;
    }

    @Override
    public void remove(long id) {
        Address byId = this.getById(id);
        DataSource.addresses.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.addresses.size();
    }

    private List<Address> createAddresss() {
        int numberOfAddresss = 10;
        for (int i = 0; i < numberOfAddresss; i++) {
            Address address = new Address();
            address.setId(i + 1);
            address.setCity("Istanbul" + (i + 1));
            address.setCountry("Turkey" + (i + 1));
            DataSource.addresses.add(address);
        }
        return DataSource.addresses;
    }


    private long getCurrentMaxId() {
        if (DataSource.addresses.isEmpty()) return 0;
        Ordering<Address> ordering = new Ordering<Address>() {
            @Override
            public int compare(Address left, Address right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.addresses).getId();
    }
}
