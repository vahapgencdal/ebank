package com.ebank.model.repository.impl;

import com.ebank.model.repository.BaseRepository;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public abstract class BaseRepositoryImpl<T> implements BaseRepository<T> {

    @Override
    public List<T> getAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T getById(int id) {
        return (T) null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
