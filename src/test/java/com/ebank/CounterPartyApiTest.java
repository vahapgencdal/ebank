package com.ebank;

import com.ebank.mock.CounterPartyMockCreater;
import com.ebank.mock.CurrencyMockCreater;
import com.ebank.model.entity.CounterParty;
import com.ebank.model.entity.Currency;
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
public class CounterPartyApiTest extends BaseApiTest {

    public CounterPartyApiTest() {
        super();
    }

    public ClientResponse insert(CounterParty counterParty) throws Exception {
        String content = json(counterParty);
        ClientResponse resp = webService.path("api").path("counterParties").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return resp;
    }

    public long insertCurrency(Currency currency) throws Exception {
        String content = json(currency);
        ClientResponse resp = webService.path("api").path("currencies").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject currencyJs = new JSONObject(resp.getEntity(String.class));
        return currencyJs.getLong("id");
    }

    @Test
    public void create() throws Exception {
        long currency = insertCurrency(CurrencyMockCreater.getTest());
        ClientResponse response = insert(CounterPartyMockCreater.getTest(currency));

        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void get() throws Exception {
        long currency = insertCurrency(CurrencyMockCreater.getTest());
        ClientResponse response = insert(CounterPartyMockCreater.getTest(currency));

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("counterParties/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void update() throws Exception {
        long currency = insertCurrency(CurrencyMockCreater.getTest());
        ClientResponse response = insert(CounterPartyMockCreater.getTest(currency));

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("counterParties/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js.put("status", false));

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals(false, jsUpdate.getBoolean("status"));
    }

    @Test
    public void remove() throws Exception {
        long currency = insertCurrency(CurrencyMockCreater.getTest());
        ClientResponse response = insert(CounterPartyMockCreater.getTest(currency));

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("counterParties/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(204, resp.getStatus());
    }


    @Test
    public void getAll() throws Exception {
        long currency = insertCurrency(CurrencyMockCreater.getTest());
        insert(CounterPartyMockCreater.getTest(currency));
        ClientResponse resp = webService.path("api").path("counterParties").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }
}
