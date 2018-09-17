package com.ebank;

import com.ebank.mock.TransactionMockCreater;
import com.ebank.model.entity.Transaction;
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
public class TransactionApiTest extends BaseApiTest {

    public TransactionApiTest() {
        super();
    }

    public ClientResponse insert(Transaction transaction) throws Exception {
        String content = json(transaction);
        ClientResponse resp = webService.path("api").path("transactions").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return resp;
    }

    @Test
    public void create() throws Exception {

        ClientResponse response = insert(TransactionMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void get() throws Exception {
        ClientResponse response = insert(TransactionMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("transactions/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void update() throws Exception {
        ClientResponse response = insert(TransactionMockCreater.getTest());

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("transactions/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js.put("status", "COMPLETED"));

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals("COMPLETED", jsUpdate.getString("status"));
    }


    @Test
    public void getSize() throws Exception {
        insert(TransactionMockCreater.getTest());

        insert(TransactionMockCreater.getTest());

        insert(TransactionMockCreater.getTest());

        ClientResponse resp = webService.path("api").path("transactions/size").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        Assert.assertEquals(6, Integer.parseInt(actual));
    }

    @Test
    public void getAll() throws Exception {
        insert(TransactionMockCreater.getTest());
        ClientResponse resp = webService.path("api").path("transactions").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }
}
