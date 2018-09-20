package com.ebank;

import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class JobApiTest extends BaseApiTest {

    public JobApiTest() {
        super();
    }


    public void startJob() throws JSONException {
        ClientResponse response = webService.path("api").path("jobs/start").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject js = new JSONObject(response.getEntity(String.class));
        Assert.assertTrue(js.getBoolean("data"));
    }

    @Test
    public void stopJob() throws JSONException {
        startJob();
        ClientResponse response = webService.path("api").path("jobs/stop").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject js = new JSONObject(response.getEntity(String.class));
        Assert.assertTrue(js.getBoolean("data"));
    }

}
