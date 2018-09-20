package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.mock.TransactionMockCreater;
import com.ebank.model.entity.Transaction;
import com.ebank.model.entity.UserAccount;
import com.ebank.util.AccountTypeEnum;
import com.ebank.util.BankEnum;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.UserEnum;
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

    public ClientResponse insert() throws Exception {
        UserAccount vahapAcc = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000);
        UserAccount emreAcc = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000);
        Transaction transaction = TransactionMockCreater.getTest(vahapAcc, emreAcc, 100, 0.2);
        String content = json(transaction);
        ClientResponse resp = webService.path("api").path("transactions").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return resp;
    }


    @Test
    public void create() throws Exception {
        ClientResponse response = insert();
        JSONObject js = new JSONObject(response.getEntity(String.class));
        Assert.assertNotNull(js.getJSONObject("data"));
    }

    @Test
    public void get() throws Exception {
        ClientResponse response = insert();
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("transactions/" + js.getJSONObject("data").getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));
        Assert.assertNotNull(getById.getJSONObject("data"));
    }

    @Test
    public void update() throws Exception {
        ClientResponse response = insert();
        JSONObject js = new JSONObject(response.getEntity(String.class));

        ClientResponse resp = webService.path("api").path("transactions/" + js.getJSONObject("data").getLong("id")).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, js.getJSONObject("data").put("status", "COMPLETED"));
        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));
        Assert.assertEquals("COMPLETED", jsUpdate.getJSONObject("data").getString("status"));
    }

    @Test
    public void getAll() throws Exception {
        insert();
        ClientResponse resp = webService.path("api").path("transactions").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));
        JSONArray jsonArray = jsUpdate.getJSONArray("data");
        Assert.assertTrue(jsonArray.length() > 0);
    }

}
