package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.entity.Account;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.request.*;
import com.ebank.util.AccountTypeEnum;
import com.ebank.util.BankEnum;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.UserEnum;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jettison.json.JSONArray;
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
    private UserAccount vhpGaranEuroAccount;


    private UserAccount vhpIsbankTlAccount;
    private UserAccount vhpIsBankUsdAccount;
    private UserAccount vhpIsBankEuroAccount;

    private UserAccount gzlIsBankTlAccount;
    private UserAccount gzlIsBankUsdAccount;

    private UserAccount esmaIsBankTlAccount;
    private UserAccount esmaIsBankUsdAccount;

    private UserAccount emreIsBankTlAccount;
    private UserAccount emreIsBankUsdAccount;

    public void initializeTest() {

        //bank own account

//      long userId, long currencyId, long accountType, String name, long bankId, boolean isDefault,long totalAmount
        vhpGaranTlAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000);
        vhpGaranTlAccountSec = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Sec Drawing Account", BankEnum.GARAN.getVal(), true, 1000);
        vhpGaranUsdAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Deposit Account", BankEnum.GARAN.getVal(), true, 1000);
        vhpGaranEuroAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.EURO.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000);

        vhpIsbankTlAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Tl Deposit Account", BankEnum.IS.getVal(), true, 1000);
        vhpIsBankUsdAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Dolar Currency Account", BankEnum.IS.getVal(), true, 1000);
        vhpIsBankEuroAccount = AccountMockCreater.getUserAccount(UserEnum.VAHAP.getVal(), CurrencyEnum.EURO.toString(), AccountTypeEnum.DRAW.toString(), "Vahap Euro Currency Account", BankEnum.IS.getVal(), true, 1000);

        gzlIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.GUZEL.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Guzel Tl Drawing Account", BankEnum.GARAN.getVal(), true, 1000);
        gzlIsBankUsdAccount = AccountMockCreater.getUserAccount(UserEnum.GUZEL.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Guzel Dolar Currency Account", BankEnum.GARAN.getVal(), true, 1000);

        esmaIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.ESMA.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Esma Tl Drawing Account", BankEnum.IS.getVal(), true, 1000);
        esmaIsBankUsdAccount = AccountMockCreater.getUserAccount(UserEnum.ESMA.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Esma Dolar Currency Account", BankEnum.IS.getVal(), true, 1000);

        emreIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Emre Tl Drawing Account", BankEnum.IS.getVal(), true, 1000);
        emreIsBankUsdAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), "Emre Dolar Currency Account", BankEnum.IS.getVal(), true, 1000);

        long gzlIsBankTlAccountId = insertAccount(gzlIsBankTlAccount, "users");
        long gzlIsBankUsdAccountId = insertAccount(gzlIsBankUsdAccount, "users");

        long vhpGaranTlAccountId = insertAccount(vhpGaranTlAccount, "users");
        long vhpGaranUsdAccountId = insertAccount(vhpGaranUsdAccount, "users");
        long vhpGaranEuroAccountId = insertAccount(vhpGaranEuroAccount, "users");
        long vhpGaranTlAccountSecId = insertAccount(vhpGaranTlAccountSec, "users");

        long vhpIsBankTlAccountId = insertAccount(vhpIsbankTlAccount, "users");
        long vhpIsBankUsdAccountId = insertAccount(vhpIsBankUsdAccount, "users");
        long vhpIsBankEuroAccountId = insertAccount(vhpIsBankEuroAccount, "users");

        long esmaIsBankTlAccountId = insertAccount(esmaIsBankTlAccount, "users");
        long esmaIsBankUsdAccountId = insertAccount(esmaIsBankUsdAccount, "users");

        long emreIsBankTlAccountId = insertAccount(emreIsBankTlAccount, "users");
        long emreIsBankUsdAccountId = insertAccount(emreIsBankUsdAccount, "users");


        gzlIsBankTlAccount.setId(gzlIsBankTlAccountId);
        gzlIsBankUsdAccount.setId(gzlIsBankUsdAccountId);

        vhpGaranTlAccount.setId(vhpGaranTlAccountId);
        vhpGaranUsdAccount.setId(vhpGaranUsdAccountId);
        vhpGaranEuroAccount.setId(vhpGaranEuroAccountId);
        vhpGaranTlAccountSec.setId(vhpGaranTlAccountSecId);

        vhpIsbankTlAccount.setId(vhpIsBankTlAccountId);
        vhpIsBankUsdAccount.setId(vhpIsBankUsdAccountId);
        vhpIsBankEuroAccount.setId(vhpIsBankEuroAccountId);

        esmaIsBankTlAccount.setId(esmaIsBankTlAccountId);
        esmaIsBankUsdAccount.setId(esmaIsBankUsdAccountId);

        emreIsBankTlAccount.setId(emreIsBankTlAccountId);
        emreIsBankUsdAccount.setId(emreIsBankUsdAccountId);

        insertAccount(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), true), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), false), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(CurrencyEnum.EURO.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.GARAN.getVal(), false), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), true), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(CurrencyEnum.USD.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), false), "banks");
        insertAccount(AccountMockCreater.getIsBankOwnAccount(CurrencyEnum.EURO.toString(), AccountTypeEnum.DRAW.toString(), BankEnum.IS.getVal(), false), "banks");

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
    public void transferAmongItsAccounts() throws JSONException, IOException {
        this.initializeTest();
        String path = "internal/among";
        AmongTransferItsAccountsRequest request = new AmongTransferItsAccountsRequest();
        request.setAmount(10);
        request.setReceiverAccountId(vhpGaranTlAccountSec.getId());
        request.setSenderAccountId(vhpGaranTlAccount.getId());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        // DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongInternalBankAccountsWithIban() throws JSONException, IOException {
        this.initializeTest();
        String path = "internal/iban";
        InternalTransferWithIbanRequest request = new InternalTransferWithIbanRequest();
        request.setAmount(10);
        request.setIban(esmaIsBankTlAccount.getIban());
        request.setSenderAccountId(vhpIsbankTlAccount.getId());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        //DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongInternalBankAccountsWithAccountNo() throws JSONException, IOException {
        this.initializeTest();
        String path = "internal/account";
        InternalTransferWithAccountNoTransferRequest request = new InternalTransferWithAccountNoTransferRequest();
        request.setAmount(10);
        request.setAccountNo(gzlIsBankTlAccount.getAccountNo());
        request.setBic(gzlIsBankTlAccount.getBank());
        request.setSenderAccountId(vhpIsbankTlAccount.getId());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        //DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongExterNalBankAccountsWithIban() throws JSONException, IOException {
        this.initializeTest();
        String path = "external/iban";
        ExternalTransferWithIbanRequest request = new ExternalTransferWithIbanRequest();
        request.setAmount(10);
        request.setIban(emreIsBankTlAccount.getIban());
        request.setSenderAccountId(vhpGaranTlAccount.getId());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        //DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongExterNalBankAccountsWithAccountNo() throws JSONException, IOException {
        this.initializeTest();
        String path = "external/account";
        ExternalTransferWithAccountNoRequest request = new ExternalTransferWithAccountNoRequest();
        request.setAmount(10);
        request.setAccountNo(emreIsBankTlAccount.getAccountNo());
        request.setBic(emreIsBankTlAccount.getBank());
        request.setSenderAccountId(vhpGaranTlAccount.getId());
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        Assert.assertTrue(js.getLong("id") > 0);

    }


    @Test
    public void get() throws Exception {
        this.initializeTest();
        ClientResponse respGet = webService.path("api").path("transactions/1").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));
        Assert.assertNotNull(getById);

    }


    @Test
    public void getAll() throws Exception {
        this.initializeTest();
        ClientResponse resp = webService.path("api").path("transactions").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }

}
