package com.ebank;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountApiTest extends BaseApiTest {

    public AccountApiTest() {
        super();
    }


/*
    @Test
    public void create() throws Exception {
        long userResponse = insertUser(UserMockCreater.getTest());
        long currencyResponse = insertCurrency(CurrencyMockCreater.getTest());
        long accountTypeResp = insertAccountType(AccountTypeMockCreater.getDrawingType());

        ClientResponse response = insert(AccountMockCreater.getBankAccount(accountTypeResp, currencyResponse, userResponse, 0));

        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void get() throws Exception {
        ClientResponse response = insertCombo();

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void update() throws Exception {
        ClientResponse response = insertCombo();

        JSONObject js = new JSONObject(response.getEntity(String.class));
        js.put("status", false);
        ClientResponse resp = webService.path("api").path("accounts/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js);

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals(false, jsUpdate.getBoolean("status"));
    }

    @Test
    public void remove() throws Exception {
        ClientResponse response = insertCombo();

        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("accounts/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(204, resp.getStatus());
    }


    @Test
    public void getAll() throws Exception {
        insertCombo();
        ClientResponse resp = webService.path("api").path("accounts").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }

    */

}
