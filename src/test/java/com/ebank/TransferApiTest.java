package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.request.AccountRequest;
import com.ebank.model.request.TransferRequest;
import com.ebank.util.*;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class TransferApiTest extends BaseApiTest {

    private AccountRequest vhpGaranAccount;
    private AccountRequest emreIsBankAccount;
    private AccountRequest emreGaranAccount;
    private AccountRequest vhpIsBankAccount;

    private void initializeTest() {

        vhpGaranAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Garan Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000));
        vhpIsBankAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), AccountTypeEnum.DRAW.toString(), "Vahap Garan Tl Drawing Account", BankEnum.GARAN.getVal(), new BigDecimal(1000));

        emreIsBankAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), AccountTypeEnum.DRAW.toString(), "Emre Tl Drawing Account", BankEnum.IS.getVal(), new BigDecimal(1000));
        emreGaranAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), AccountTypeEnum.DRAW.toString(), "Emre Tl Drawing Account", BankEnum.IS.getVal(), new BigDecimal(1000));


        long vhpGaranAccountId = insertAccount(vhpGaranAccount, "users");
        long vhpIsBankAccountId = insertAccount(vhpIsBankAccount, "users");
        long emreIsBankAccountId = insertAccount(emreIsBankAccount, "users");
        long emreGaranAccountId = insertAccount(emreGaranAccount, "users");

        vhpGaranAccount.setId(vhpGaranAccountId);
        emreIsBankAccount.setId(emreIsBankAccountId);
        vhpIsBankAccount.setId(vhpIsBankAccountId);
        emreGaranAccount.setId(emreGaranAccountId);

        insertAccount(AccountMockCreater.getGarantiOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), new BigDecimal(1000)), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), new BigDecimal(1000)), "banks");

    }


    public TransferApiTest() {
        super();
    }

    private long insertAccount(AccountRequest account, String path) {
        try {
            String content = json(account);
            ClientResponse resp = webService.path("api").path("accounts" + "/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject js = new JSONObject(resp.getEntity(String.class));
            return js.getJSONObject("data").getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private JSONObject getAccount(String accountNo, String bic, String path) throws JSONException {
        ClientResponse respGet = webService.path("api").path("accounts/" + path + "/account/" + accountNo + "/" + bic).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        return new JSONObject(respGet.getEntity(String.class));
    }


    private JSONObject transfer(AccountRequest sender, AccountRequest receiver, String senderCurrency, String receiverCurrency, BigDecimal amount) throws IOException, JSONException {
        TransferRequest request = new TransferRequest();
        request.setAmount(amount);
        request.setReceiverAccountNo(receiver.getAccountNo());
        request.setReceiverBankCode(receiver.getBank());
        request.setSenderAccountNo(sender.getAccountNo());
        request.setSenderBankCode(sender.getBank());
        request.setSenderCurrencyCode(senderCurrency);
        request.setReceiverCurrencyCode(receiverCurrency);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        return new JSONObject(resp.getEntity(String.class));
    }

    private String getFromJsonObject(JSONObject js, String currency) {
        try {
            JSONArray jsArray = js.getJSONObject("data").getJSONArray("amounts");
            for (int i = 0; i < jsArray.length(); i++) {
                if (jsArray.getJSONObject(i).getJSONObject("currency").getString("currencyCode").equals(currency)) {
                    return jsArray.getJSONObject(i).getString("number");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void transferSameBank() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, emreGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(100));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        MonetaryAmount fee = StaticUtil.getTotalFeeAmount(BankEnum.GARAN.getVal(), BankEnum.GARAN.getVal(), Monetary.getDefaultAmountFactory().setCurrency(CurrencyEnum.EUR.name()).setNumber(100).create());

        String result = vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).subtract(new BigDecimal(100)).subtract(new BigDecimal(fee.getNumber().doubleValue())).toString();
        Assert.assertEquals(result, getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    @Test
    public void transferSameBankNegative() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, emreGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(-1));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        Assert.assertEquals(vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).toString(), getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    @Test
    public void transferSameBankVeryBigAmount() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, emreGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(122000222));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        Assert.assertEquals(vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).toString(), getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    @Test
    public void transferExactlySameAccounts() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, vhpGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(100));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        Assert.assertEquals(vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).toString(), getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    //Different bank
    @Test
    public void transferDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, vhpIsBankAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(200));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        MonetaryAmount fee = StaticUtil.getTotalFeeAmount(BankEnum.IS.getVal(), BankEnum.GARAN.getVal(), Monetary.getDefaultAmountFactory().setCurrency(CurrencyEnum.EUR.name()).setNumber(200).create());

        String result = vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).subtract(new BigDecimal(200)).subtract(new BigDecimal(fee.getNumber().doubleValue())).toString();
        Assert.assertEquals(result, getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    @Test
    public void transferDifferentBankNegative() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpIsBankAccount, vhpGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(-1));
        JSONObject account = getAccount(vhpIsBankAccount.getAccountNo(), vhpIsBankAccount.getBank(), "users");
        Assert.assertEquals(vhpIsBankAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).toString(), getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    @Test
    public void transferDifferentBankBigNumber() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpIsBankAccount, vhpGaranAccount, CurrencyEnum.EUR.name(), CurrencyEnum.EUR.name(), new BigDecimal(1111111111));
        JSONObject account = getAccount(vhpIsBankAccount.getAccountNo(), vhpIsBankAccount.getBank(), "users");
        Assert.assertEquals(vhpIsBankAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).toString(), getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }

    //exchange currency
    @Test
    public void transferDifferentBankDifferentCurrencies() throws JSONException, IOException {
        this.initializeTest();
        transfer(vhpGaranAccount, emreIsBankAccount, CurrencyEnum.EUR.name(), CurrencyEnum.USD.name(), new BigDecimal(100));
        JSONObject account = getAccount(vhpGaranAccount.getAccountNo(), vhpGaranAccount.getBank(), "users");

        MonetaryAmount fee = StaticUtil.getTotalFeeAmount(BankEnum.IS.getVal(), BankEnum.GARAN.getVal(), Monetary.getDefaultAmountFactory().setCurrency(CurrencyEnum.EUR.name()).setNumber(100).create());

        String result = vhpGaranAccount.getAmountByCuurency(CurrencyEnum.EUR.name()).subtract(new BigDecimal(100)).subtract(new BigDecimal(fee.getNumber().doubleValue())).toString();
        Assert.assertEquals(result, getFromJsonObject(account, CurrencyEnum.EUR.name()));
    }


}
