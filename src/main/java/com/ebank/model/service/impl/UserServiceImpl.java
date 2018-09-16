package com.ebank.model.service.impl;

import com.ebank.model.entity.User;
import com.ebank.model.repository.UserRepository;
import com.ebank.model.service.UserService;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.getAll();
    }

    @Override
    public User getById(long id) {
        return this.userRepository.getById(id);
    }

    @Override
    public User create(User user) {
        return this.userRepository.create(user);
    }

    @Override
    public User update(User user) {
        return this.userRepository.update(user);
    }

    @Override
    public void remove(long id) {
        this.userRepository.remove(id);
    }

    @Override
    public int getSize() {
        return this.userRepository.getSize();
    }
}
