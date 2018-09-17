package com.ebank;

import com.ebank.mock.AddressMockCreater;
import com.ebank.model.entity.Address;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
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
public class AddressApiTest extends BaseApiTest {

    public AddressApiTest() {
        super();
    }

    public ClientResponse insert(Address user) throws Exception {
        String content = json(user);
        ClientResponse resp = webService.path("api").path("addresses").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return resp;
    }

    @Test
    public void create() throws Exception {

        ClientResponse response = insert(AddressMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void get() throws Exception {
        ClientResponse response = insert(AddressMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("addresses/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void update() throws Exception {
        ClientResponse response = insert(AddressMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("addresses/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js.put("status", false));

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals(false, jsUpdate.getBoolean("status"));
    }

    @Test
    public void remove() throws Exception {
        ClientResponse response = insert(AddressMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("addresses/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(204, resp.getStatus());
    }


    @Test
    public void getAll() throws Exception {
        insert(AddressMockCreater.getTest());
        ClientResponse resp = webService.path("api").path("addresses").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }
}
