package com.ebank.conf;

import com.ebank.model.repository.BankAccountRepository;
import com.ebank.model.repository.TransactionRepository;
import com.ebank.model.repository.UserAccountRepository;
import com.ebank.model.repository.impl.BankAccountRepositoryImpl;
import com.ebank.model.repository.impl.TransactionRepositoryImpl;
import com.ebank.model.repository.impl.UserAccountRepositoryImpl;
import com.ebank.model.service.BankAccountService;
import com.ebank.model.service.TransactionService;
import com.ebank.model.service.TransferService;
import com.ebank.model.service.UserAccountService;
import com.ebank.model.service.impl.BankAccountServiceImpl;
import com.ebank.model.service.impl.TransactionServiceImpl;
import com.ebank.model.service.impl.TransferServiceImpl;
import com.ebank.model.service.impl.UserAccountServiceImpl;
import com.google.inject.AbstractModule;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 14.09.2018
 * @description TODO: Class Description
 */
public class AppBinder extends AbstractModule {
    @Override
    protected void configure() {

        bind(BankAccountRepository.class).to(BankAccountRepositoryImpl.class);
        bind(BankAccountService.class).to(BankAccountServiceImpl.class);

        bind(UserAccountRepository.class).to(UserAccountRepositoryImpl.class);
        bind(UserAccountService.class).to(UserAccountServiceImpl.class);

        bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);

        bind(TransferService.class).to(TransferServiceImpl.class);

    }
}
