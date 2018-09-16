package com.ebank.conf;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class ClientProvider {
    private final ServerProvider serverProvider;

    public ClientProvider(ServerProvider serverProvider) {
        this.serverProvider = serverProvider;
    }

    public WebResource getWebResource() {
        Client client = Client.create(new DefaultClientConfig());
        return client.resource(serverProvider.getBaseURI());
    }

}
