package com.ebank.model.service;

import com.ebank.model.entity.User;

import java.util.List;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface UserService {

    List<User> getAllUsers();

    User getById(int id);

    User createNewUser(User user);

    User update(User user);

    void remove(int id);

    int getNumberOfUsers();
}
