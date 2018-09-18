package com.ebank.conf;

import com.ebank.model.repository.*;
import com.ebank.model.repository.impl.*;
import com.ebank.model.service.*;
import com.ebank.model.service.impl.*;
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

        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);

        bind(BankAccountRepository.class).to(BankAccountRepositoryImpl.class);
        bind(BankAccountService.class).to(BankAccountServiceImpl.class);

        bind(UserAccountRepository.class).to(UserAccountRepositoryImpl.class);
        bind(UserAccountService.class).to(UserAccountServiceImpl.class);

        bind(AccountTypeRepository.class).to(AccountTypeRepositoryImpl.class);
        bind(AccountTypeService.class).to(AccountTypeServiceImpl.class);

        bind(AddressRepository.class).to(AddressRepositoryImpl.class);
        bind(AddressService.class).to(AddressServiceImpl.class);

        bind(BankRepository.class).to(BankRepositoryImpl.class);
        bind(BankService.class).to(BankServiceImpl.class);

        bind(CounterPartyRepository.class).to(CounterPartyRepositoryImpl.class);
        bind(CounterPartyService.class).to(CounterPartyServiceImpl.class);

        bind(CurrencyRepository.class).to(CurrencyRepositoryImpl.class);
        bind(CurrencyService.class).to(CurrencyServiceImpl.class);

        bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);

        bind(TransferService.class).to(TransferServiceImpl.class);

    }
}
