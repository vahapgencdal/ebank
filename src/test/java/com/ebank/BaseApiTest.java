package com.ebank;

import com.ebank.conf.ClientProvider;
import com.ebank.conf.ServerProvider;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description TODO: Class Description
 */
public abstract class BaseApiTest {
    private final ServerProvider serverProvider;
    private final ClientProvider clientProvider;
    protected WebResource webService;


    private final ObjectMapper objectMapper;

    public BaseApiTest() {
        serverProvider = new ServerProvider();
        clientProvider = new ClientProvider(serverProvider);
        objectMapper = new ObjectMapper();
    }

    @Before
    public void startServer() throws IOException {
        serverProvider.createServer();
        webService = clientProvider.getWebResource();

    }

    @After
    public void stopServer() {
        serverProvider.stop();
    }

    protected String json(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }
}
