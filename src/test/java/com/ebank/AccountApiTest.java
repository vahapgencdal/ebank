package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.entity.Account;
import com.ebank.util.AccountTypeEnum;
import com.ebank.util.BankEnum;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.UserEnum;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.MediaType;

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

    public ClientResponse insert(Account account, String path) throws Exception {
        String content = json(account);
        ClientResponse resp = webService.path("api").path("accounts/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return resp;
    }


    @Test
    public void createUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void getUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/users/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void updateUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));
        js.put("name", "XXX");

        ClientResponse resp = webService.path("api").path("accounts/users/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js);

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals("XXX", jsUpdate.getString("name"));
    }

    @Test
    public void removeUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("accounts/users/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(204, resp.getStatus());
    }


    @Test
    public void getAllUsers() throws Exception {
        insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000), "users");

        ClientResponse resp = webService.path("api").path("accounts/users").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }


    @Test
    public void createBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");

        Assert.assertNotNull(response);
    }

    @Test
    public void getBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/banks/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void updateBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));
        js.put("name", "XXX");

        ClientResponse resp = webService.path("api").path("accounts/banks/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js);

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals("XXX", jsUpdate.getString("name"));
    }

    @Test
    public void removeBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("accounts/banks/" + js.getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(204, resp.getStatus());
    }


    @Test
    public void getAllBankAccounts() throws Exception {
        insert(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        ClientResponse resp = webService.path("api").path("accounts/banks").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }


}
