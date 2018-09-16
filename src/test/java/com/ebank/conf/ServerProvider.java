package com.ebank.conf;

import com.ebank.model.repository.*;
import com.ebank.model.repository.impl.*;
import com.ebank.model.service.*;
import com.ebank.model.service.impl.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class ServerProvider {

    private final URI BASE_URI = getBaseURI();
    private HttpServer server;

    protected URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(8081).build();
    }

    public void createServer() throws IOException {
        System.out.println("Starting grizzly...");

        Injector injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(UserService.class).to(UserServiceImpl.class);
                bind(UserRepository.class).to(UserRepositoryImpl.class);

                bind(AccountRepository.class).to(AccountRepositoryImpl.class);
                bind(AccountService.class).to(AccountServiceImpl.class);

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

                bind(TransactionTypeRepository.class).to(TransactionTypeRepositoryImpl.class);
                bind(TransactionTypeService.class).to(TransactionTypeServiceImpl.class);

                bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
            }
        });

        ResourceConfig rc = new PackagesResourceConfig("com.ebank.api");
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc, injector);
        server = GrizzlyServerFactory.createHttpServer(BASE_URI + "api/", rc, ioc);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapi/application.wadl\n" +
                        "Hit enter to stop it...",
                BASE_URI));
    }

    public void stop() {
        server.stop();
    }
}
