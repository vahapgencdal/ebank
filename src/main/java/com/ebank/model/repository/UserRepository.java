package com.ebank.model.repository;

import com.ebank.model.entity.User;
import com.ebank.model.repository.BaseRepository;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public interface UserRepository extends BaseRepository<User> {
    User create(User user);

    User update(User user);

    void remove(int id);

    int getNumberOfUsers();
}
