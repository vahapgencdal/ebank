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
        return null;
    }

    @Override
    public T getById(long id) {
        return null;
    }

    @Override
    public T create(T t) {
        return null;
    }

    @Override
    public T update(T t) {
        return null;
    }

    @Override
    public void remove(long id) {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
