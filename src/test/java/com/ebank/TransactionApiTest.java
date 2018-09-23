package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.request.AccountRequest;
import com.ebank.model.request.TransactionRequest;
import com.ebank.util.*;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

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

    private ClientResponse insert() throws Exception {
        AccountRequest vahapAcc = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(100));
        AccountRequest emreAcc = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(100));

        MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).setNumber(new BigDecimal(100)).create();

        TransactionRequest transaction = TransactionCreater.createTransactionRequest(vahapAcc, emreAcc, monetaryAmount, monetaryAmount.multiply(new BigDecimal(0.2)));
        String content = json(transaction);
        return webService.path("api").path("transactions").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
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


        AccountRequest vahapAcc = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(100));
        AccountRequest emreAcc = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(100));

        MonetaryAmount monetaryAmount = Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(CurrencyEnum.EUR.name())).setNumber(new BigDecimal(100)).create();

        TransactionRequest transaction = TransactionCreater.createTransactionRequest(vahapAcc, emreAcc, monetaryAmount, monetaryAmount.multiply(new BigDecimal(0.2)));
        String content = json(transaction);

        ClientResponse resp = webService.path("api").path("transactions/" + js.getJSONObject("data").getLong("id")).
                type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, content);
        JSONObject jsUpdate = new JSONObject(resp.getEntity(String.class));
        Assert.assertEquals(0L, jsUpdate.getJSONObject("data").getLong("id"));
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
