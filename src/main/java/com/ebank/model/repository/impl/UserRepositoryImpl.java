package com.ebank.model.repository.impl;

import com.ebank.model.entity.User;
import com.ebank.model.repository.UserRepository;
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
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

    private List<User> users;

    public UserRepositoryImpl() {
        this.users = new ArrayList<>();
        this.users = this.createUsers();
    }

    public User getById(long id) {
        return this.users.parallelStream().filter(user -> user.getId() == id).findAny().orElse(new User());
    }

    public List<User> getAll() {
        return this.users;
    }

    @Override
    public User create(User user) {
        user.setId(getCurrentMaxId() + 1);
        this.users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        User byId = this.getById(user.getId());
        byId.setFirstName(user.getFirstName());
        byId.setLastName(user.getLastName());
        return byId;
    }

    @Override
    public void remove(long id) {
        User byId = this.getById(id);
        this.users.remove(byId);
    }

    @Override
    public int getSize() {
        return this.users.size();
    }

    private List<User> createUsers() {
        int numberOfUsers = 10;
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setFirstName("Vahap" + (i + 1));
            user.setLastName("Genc" + (i + 1));
            this.users.add(user);
        }
        return this.users;
    }


    private long getCurrentMaxId() {
        Ordering<User> ordering = new Ordering<User>() {
            @Override
            public int compare(User left, User right) {
                return Long.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.users).getId();
    }
}
