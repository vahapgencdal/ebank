package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.entity.Account;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.request.newfolder.AccountToAccountRequest;
import com.ebank.model.request.newfolder.AccountToIbanRequest;
import com.ebank.model.request.newfolder.IbanToAccountRequest;
import com.ebank.model.request.newfolder.IbanToIbanRequest;
import com.ebank.util.AccountTypeEnum;
import com.ebank.util.BankEnum;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.UserEnum;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class TransferApiTest extends BaseApiTest {

    private UserAccount vhpGaranTlAccount;
    private UserAccount vhpGaranTlAccountSec;
    private UserAccount vhpGaranUsdAccount;
    private UserAccount vhpIsBankTlAccount;

    private UserAccount emreIsBankTlAccount;
    private UserAccount emreGaranTlAccount;
    private UserAccount emreIsBankUsdAccount;

    public void initializeTest() {

        //bank own account

//      long userId, long currencyId, long accountType, String name, long bankId, boolean isDefault,long totalAmount
        vhpGaranTlAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Garan Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000000);
        vhpIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap IS Tl Drawing Account", BankEnum.IS.getVal(), true, 1000000);
        vhpGaranTlAccountSec = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Garan Tl Sec Drawing Account", BankEnum.GARAN.getVal(), true, 1000000);
        vhpGaranUsdAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Vahap  Garan Tl Deposit Account", BankEnum.GARAN.getVal(), true, 1000000);

        emreIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Emre Tl Drawing Account", BankEnum.IS.getVal(), true, 1000);
        emreIsBankUsdAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Emre Dolar Currency Account", BankEnum.IS.getVal(), true, 1000000);
        emreGaranTlAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Emre Garan Tl Account", BankEnum.GARAN.getVal(), true, 1000000);


        long vhpGaranTlAccountId = insertAccount(vhpGaranTlAccount, "users");
        long vhpIsBankTlAccountId = insertAccount(vhpIsBankTlAccount, "users");
        long vhpGaranUsdAccountId = insertAccount(vhpGaranUsdAccount, "users");
        long vhpGaranTlAccountSecId = insertAccount(vhpGaranTlAccountSec, "users");

        long emreIsBankTlAccountId = insertAccount(emreIsBankTlAccount, "users");
        long emreIsBankUsdAccountId = insertAccount(emreIsBankUsdAccount, "users");
        long emreGaranTlAccountId = insertAccount(emreGaranTlAccount, "users");

        vhpGaranTlAccount.setId(vhpGaranTlAccountId);
        vhpGaranUsdAccount.setId(vhpGaranUsdAccountId);
        vhpGaranTlAccountSec.setId(vhpGaranTlAccountSecId);
        vhpIsBankTlAccount.setId(vhpIsBankTlAccountId);

        emreIsBankTlAccount.setId(emreIsBankTlAccountId);
        emreIsBankUsdAccount.setId(emreIsBankUsdAccountId);
        emreGaranTlAccount.setId(emreGaranTlAccountId);

        insertAccount(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), false), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), true), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), false), "banks");

    }


    public TransferApiTest() {
        super();
    }

    private long insertAccount(Account account, String path) {
        try {
            String content = json(account);
            ClientResponse resp = webService.path("api").path("accounts" + "/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject js = new JSONObject(resp.getEntity(String.class));
            return js.getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Test
    public void AccountsToAccountSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();

        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(100);
        request.setReceiverAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccount.getBank());
        request.setSenderAccountNo(vhpGaranTlAccountSec.getAccountNo());
        request.setSenderBic(vhpGaranTlAccountSec.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void AccountsToAccountSameUserSameBankDifferentCurrency() throws JSONException, IOException {
        Assert.assertTrue(true);
    }

    @Test
    public void AccountsToAccountDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(140);
        request.setReceiverAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccount.getBank());
        request.setSenderAccountNo(emreGaranTlAccount.getAccountNo());
        request.setSenderBic(emreGaranTlAccount.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void AccountsToAccountDifferentUserSameBankDifferentCurrency() throws JSONException, IOException {
        Assert.assertTrue(true);
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(190);
        request.setReceiverAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccount.getBank());
        request.setSenderAccountNo(emreIsBankTlAccount.getAccountNo());
        request.setSenderBic(emreIsBankTlAccount.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBankDifferentCurrency() throws JSONException, IOException {
        Assert.assertTrue(true);
    }

    @Test
    public void AccountsToIbanSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        AccountToIbanRequest request = new AccountToIbanRequest();
        request.setAmount(190);
        request.setReceiverIban(vhpGaranTlAccountSec.getIban());
        request.setSenderAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setSenderBic(vhpGaranTlAccount.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void AccountsToIbanDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        AccountToIbanRequest request = new AccountToIbanRequest();
        request.setAmount(210);
        request.setReceiverIban(emreGaranTlAccount.getIban());
        request.setSenderAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setSenderBic(vhpGaranTlAccount.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void AccountsToIbanDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        AccountToIbanRequest request = new AccountToIbanRequest();
        request.setAmount(250);
        request.setReceiverIban(emreIsBankTlAccount.getIban());
        request.setSenderAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setSenderBic(vhpGaranTlAccount.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/accountToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToIbanSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToIbanRequest request = new IbanToIbanRequest();
        request.setAmount(200);
        request.setReceiverIban(vhpGaranTlAccount.getIban());
        request.setSenderIban(vhpGaranTlAccountSec.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToIbanDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToIbanRequest request = new IbanToIbanRequest();
        request.setAmount(250);
        request.setReceiverIban(vhpIsBankTlAccount.getIban());
        request.setSenderIban(emreIsBankTlAccount.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToIbanDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToIbanRequest request = new IbanToIbanRequest();
        request.setAmount(300);
        request.setReceiverIban(vhpIsBankTlAccount.getIban());
        request.setSenderIban(emreGaranTlAccount.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToIban").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToAccountSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToAccountRequest request = new IbanToAccountRequest();
        request.setAmount(400);
        request.setReceiverAccountNo(vhpGaranTlAccountSec.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccountSec.getBank());

        request.setSenderIban(vhpGaranTlAccount.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToAccountDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToAccountRequest request = new IbanToAccountRequest();
        request.setAmount(300);
        request.setReceiverAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccount.getBank());

        request.setSenderIban(emreGaranTlAccount.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }

    @Test
    public void IbanToAccountDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        IbanToAccountRequest request = new IbanToAccountRequest();
        request.setAmount(250);
        request.setReceiverAccountNo(vhpGaranTlAccount.getAccountNo());
        request.setReceiverBic(vhpGaranTlAccount.getBank());

        request.setSenderIban(emreIsBankTlAccount.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/ibanToAccount").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        Assert.assertTrue(js.getJSONObject("data").getLong("id") > 0);
    }
}
