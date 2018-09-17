package com.ebank.model.repository.impl;

import com.ebank.datasource.DataSource;
import com.ebank.model.entity.User;
import com.ebank.model.repository.UserRepository;
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
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    public User getById(long id) {
        return DataSource.users.parallelStream().filter(user -> user.getId() == id).findAny().orElse(new User());
    }

    public List<User> getAll() {
        return DataSource.users;
    }

    @Override
    public User create(User user) {
        user.setId(getCurrentMaxId() + 1);
        DataSource.users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        User byId = this.getById(user.getId());
        byId.setStatus(user.isStatus());
        byId.setEmail(user.getEmail());
        return byId;
    }

    @Override
    public void remove(long id) {
        User byId = this.getById(id);
        DataSource.users.remove(byId);
    }

    @Override
    public int getSize() {
        return DataSource.users.size();
    }

    private List<User> createUsers() {
        int numberOfUsers = 10;
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setId(i + 1);
            DataSource.users.add(user);
        }
        return DataSource.users;
    }


    private long getCurrentMaxId() {
        if (DataSource.users.isEmpty()) return 0;
        Ordering<User> ordering = new Ordering<User>() {
            @Override
            public int compare(User left, User right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(DataSource.users).getId();
    }
}
