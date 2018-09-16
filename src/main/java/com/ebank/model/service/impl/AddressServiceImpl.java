package com.ebank.model.service.impl;

import com.ebank.model.entity.Address;
import com.ebank.model.repository.AddressRepository;
import com.ebank.model.service.AddressService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@Singleton
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Inject
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAll() {
        return this.addressRepository.getAll();
    }

    @Override
    public Address getById(long id) {
        return this.addressRepository.getById(id);
    }

    @Override
    public Address create(Address address) {
        return this.addressRepository.create(address);
    }

    @Override
    public Address update(Address address) {
        return this.addressRepository.update(address);
    }

    @Override
    public void remove(long id) {
        this.addressRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.addressRepository.getSize();
    }
}
