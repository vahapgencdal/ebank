package com.ebank.model.repository;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface BaseRepository<T> {

    List<T> getAll();

    T getById(int id);
}
