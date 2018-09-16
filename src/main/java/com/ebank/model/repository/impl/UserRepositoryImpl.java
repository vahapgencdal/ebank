package com.ebank.model.repository.impl;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.google.inject.Singleton;
import com.ebank.model.entity.User;
import com.ebank.model.repository.UserRepository;

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

    private List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
        this.users = this.createUsers();
    }

    public User getById(int id) {
        for (User u : this.users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return new User();
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
    public void remove(int id) {
        User byId = this.getById(id);
        this.users.remove(byId);
    }

    @Override
    public int getNumberOfUsers() {
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

    private int getCurrentMaxId() {
        Ordering<User> ordering = new Ordering<User>() {
            @Override
            public int compare(User left, User right) {
                return Ints.compare(left.getId(), right.getId());
            }
        };
        return ordering.max(this.users).getId();
    }
}
