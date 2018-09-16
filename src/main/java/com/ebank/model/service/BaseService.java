package com.ebank.model.service;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public interface BaseService<T> {
    List<T> getAll();

    T getById(long id);

    T create(T user);

    T update(T user);

    void remove(long id);

    int getSize();
}
