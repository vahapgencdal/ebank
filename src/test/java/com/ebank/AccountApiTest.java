package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.request.AccountRequest;
import com.ebank.util.AccountTypeEnum;
import com.ebank.util.BankEnum;
import com.ebank.util.UserEnum;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

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

    private ClientResponse insert(AccountRequest account, String path) throws Exception {
        String content = json(account);
        return webService.path("api").path("accounts/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
    }


    @Test
    public void createUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000)), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        Assert.assertNotNull(js);
    }

    @Test
    public void getUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000)), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/users/" + js.getJSONObject("data").getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void updateUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000)), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));
        js.getJSONObject("data").put("name", "XXX");

        AccountRequest req = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000));
        req.setId(js.getJSONObject("data").getLong("id"));
        req.setName("XXX");
        String content = json(req);
        ClientResponse resp = webService.path("api").path("accounts/users/" + req.getId()).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, content);

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals("XXX", jsUpdate.getJSONObject("data").getString("name"));
    }

    @Test
    public void removeUser() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000)), "users");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("accounts/users/" + js.getJSONObject("data").getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(200, resp.getStatus());
    }


    @Test
    public void getAllUsers() throws Exception {
        insert(AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000)), "users");

        ClientResponse resp = webService.path("api").path("accounts/users").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONObject jsonObject = new JSONObject(actual);
        Assert.assertTrue(jsonObject.getJSONArray("data").length() > 0);
    }


    @Test
    public void createBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));
        Assert.assertNotNull(js.getJSONObject("data"));
    }

    @Test
    public void getBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/banks/" + js.getJSONObject("data").getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void getBankAccountByNoAndBic() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("accounts/banks/account/" + js.getJSONObject("data").get("accountNo") + "/" + js.getJSONObject("data").get("bank")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));

        Assert.assertNotNull(getById);
    }

    @Test
    public void updateBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        AccountRequest req = AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000));
        req.setId(js.getJSONObject("data").getLong("id"));
        req.setName("XXX");
        String content = json(req);
        ClientResponse resp = webService.path("api").path("accounts/banks/" + req.getId()).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, content);

        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));

        Assert.assertEquals("XXX", jsUpdate.getJSONObject("data").getString("name"));
    }

    @Test
    public void removeBankAccount() throws Exception {
        ClientResponse response = insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("accounts/banks/" + js.getJSONObject("data").getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        Assert.assertEquals(200, resp.getStatus());
    }


    @Test
    public void getAllBankAccounts() throws Exception {
        insert(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        ClientResponse resp = webService.path("api").path("accounts/banks").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONObject jsonObject = new JSONObject(actual);
        Assert.assertTrue(jsonObject.getJSONArray("data").length() > 0);
    }


}
