package com.ebank.conf;

import com.ebank.executer.TransactionThreadPoolExecuter;
import com.ebank.executer.TransactionThreadPoolExecuterImpl;
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
                bind(BankAccountRepository.class).to(BankAccountRepositoryImpl.class);
                bind(BankAccountService.class).to(BankAccountServiceImpl.class);

                bind(UserAccountRepository.class).to(UserAccountRepositoryImpl.class);
                bind(UserAccountService.class).to(UserAccountServiceImpl.class);

                bind(TransactionRepository.class).to(TransactionRepositoryImpl.class);
                bind(TransactionService.class).to(TransactionServiceImpl.class);

                bind(TransferService.class).to(TransferServiceImpl.class);
                bind(TransactionThreadPoolExecuter.class).to(TransactionThreadPoolExecuterImpl.class);

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
