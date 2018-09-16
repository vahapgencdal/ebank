package com.ebank.conf;

import com.google.inject.AbstractModule;
import com.ebank.model.repository.UserRepository;
import com.ebank.model.repository.impl.UserRepositoryImpl;
import com.ebank.model.service.UserService;
import com.ebank.model.service.impl.UserServiceImpl;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 14.09.2018
 * @description TODO: Class Description
 */
public class AppBind extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
    }
}
