package com.ebank;

import com.ebank.datasource.DataSource;
import com.ebank.mock.*;
import com.ebank.model.entity.*;
import com.ebank.model.request.*;
import com.ebank.util.CurrencyEnum;
import com.ebank.util.UserTypeEnum;
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

    private static long euroId;
    private static long usdId;
    private static long tlId;

    private static long garanBankId;
    private static long isBankId;

    private static String garanBankBic;
    private static String isBankBic;

    private static String vhpIban;
    private static String emreIban;
    private static String gzlIban;
    private static String esmaIban;

    private static String vhpAccount;
    private static String emreAccount;
    private static String gzlAccount;
    private static String esmaAccount;

    private static long vhpGaranTlAccountId;
    private static long vhpGaranTlAccountDepId;

    public void initializeTest() {
        euroId = insertCurrency(CurrencyMockCreater.create("Euro", CurrencyEnum.EURO.name(), "Euro"));
        usdId = insertCurrency(CurrencyMockCreater.create("Dolar", CurrencyEnum.USD.name(), "American dolar"));
        tlId = insertCurrency(CurrencyMockCreater.create("Turkish lira", CurrencyEnum.TL.name(), "Turkish Lira"));

        long addressId = insertAddress(AddressMockCreater.getTest());
        Bank garanbank = BankMockCreater.getGarantiBank(addressId);
        garanBankBic = garanbank.getBic();
        garanBankId = insertBank(garanbank);
        Bank isBank = BankMockCreater.getIsBank(addressId);
        isBankBic = isBank.getBic();
        isBankId = insertBank(isBank);


        //long addressId, String fullName, String email, String phone, String userType, long bankId
        long vhpUser = insertUser(UserMockCreater.getUser(addressId, "Vahap", "vhp@gmail.com", "5423711235", UserTypeEnum.IND.toString(), garanBankId));
        long gzlUser = insertUser(UserMockCreater.getUser(addressId, "Guzel", "guzel@gmail.com", "5424721236", UserTypeEnum.IND.toString(), garanBankId));
        long esmaUser = insertUser(UserMockCreater.getUser(addressId, "Esma", "esma@gmail.com", "5426731237", UserTypeEnum.IND.toString(), isBankId));
        long emreUser = insertUser(UserMockCreater.getUser(addressId, "Emre", "emre@gmail.com", "5473741238", UserTypeEnum.IND.toString(), isBankId));


        //bank own account

//long userId, long currencyId, long accountType, String name, long bankId, boolean isDefault,long totalAmount
        UserAccount vhpGaranTlAccount = AccountMockCreater.getUserAccount(vhpUser, tlId, 0, "Tl Drawing Account", garanBankId, true, 1000);
        UserAccount vhpGaranTlAccountDep = AccountMockCreater.getUserAccount(vhpUser, tlId, 0, "Tl Deposit Account", garanBankId, true, 1000);
        vhpIban = vhpGaranTlAccount.getIban();
        vhpAccount = vhpGaranTlAccount.getAccountNo();
        vhpGaranTlAccountId = insertAccount(vhpGaranTlAccount, "users");
        vhpGaranTlAccountDepId = insertAccount(vhpGaranTlAccountDep, "users");
        long vhpIsBankUsdAccount = insertAccount(AccountMockCreater.getUserAccount(vhpUser, usdId, 0, "Dolar Currency Account", garanBankId, true, 1000), "users");
        long vhpIsBankEuroAccount = insertAccount(AccountMockCreater.getUserAccount(vhpUser, euroId, 0, "Euro Currency Account", garanBankId, true, 1000), "users");

        UserAccount gzlIsBankTlAccount = AccountMockCreater.getUserAccount(gzlUser, tlId, 0, "Tl Drawing Account", garanBankId, true, 1000);
        gzlIban = gzlIsBankTlAccount.getIban();
        gzlAccount = gzlIsBankTlAccount.getAccountNo();
        long gzlIsBankTlAccountId = insertAccount(gzlIsBankTlAccount, "users");
        long gzlIsBankUsdAccount = insertAccount(AccountMockCreater.getUserAccount(gzlUser, usdId, 0, "Dolar Currency Account", garanBankId, true, 1000), "users");

        UserAccount esmaIsBankTlAccount = AccountMockCreater.getUserAccount(esmaUser, tlId, 0, "Tl Drawing Account", isBankId, true, 1000);
        esmaIban = esmaIsBankTlAccount.getIban();
        esmaAccount = esmaIsBankTlAccount.getAccountNo();
        long esmaIsBankTlAccountId = insertAccount(esmaIsBankTlAccount, "users");
        long esmaIsBankUsdAccount = insertAccount(AccountMockCreater.getUserAccount(esmaUser, usdId, 0, "Dolar Currency Account", isBankId, true, 1000), "users");

        UserAccount emreIsBankTlAccount = AccountMockCreater.getUserAccount(emreUser, tlId, 0, "Tl Drawing Account", isBankId, true, 1000);
        emreIban = emreIsBankTlAccount.getIban();
        emreAccount = emreIsBankTlAccount.getAccountNo();
        long emreIsBankTlAccountId = insertAccount(emreIsBankTlAccount, "users");
        long emreIsBankUsdAccount = insertAccount(AccountMockCreater.getUserAccount(emreUser, usdId, 0, "Dolar Currency Account", isBankId, true, 1000), "users");

        //long userId, long currencyId, long accountType, long bankId, boolean isDefault
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, tlId, 0, isBankId, true), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, usdId, 0, isBankId, false), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, euroId, 0, isBankId, false), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, tlId, 0, garanBankId, true), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, usdId, 0, garanBankId, false), "banks");
        insertAccount(AccountMockCreater.getGarantiOwnAccount(0, euroId, 0, garanBankId, false), "banks");


    }


    public TransferApiTest() {
        super();
    }

    public long insertAccount(Account account, String path) {
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

    public long insertUser(User user) {
        try {
            String content = json(user);
            ClientResponse resp = webService.path("api").path("users").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject js = new JSONObject(resp.getEntity(String.class));
            return js.getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    private long insertAddress(Address user) {
        try {
            String content = json(user);
            ClientResponse resp = webService.path("api").path("addresses").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject js = new JSONObject(resp.getEntity(String.class));
            return js.getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long insertBank(Bank user) {
        try {
            String content = json(user);
            ClientResponse resp = webService.path("api").path("banks").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject bankJs = new JSONObject(resp.getEntity(String.class));
            return bankJs.getLong("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }


    private long insertCurrency(Currency currency) {
        try {
            String content = json(currency);
            ClientResponse resp = webService.path("api").path("currencies").type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
            JSONObject currencyJs = new JSONObject(resp.getEntity(String.class));
            return currencyJs.getLong("id");
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
        request.setAmount(100);
        request.setReceiverAccountId(vhpGaranTlAccountDepId);
        request.setSenderAccountId(vhpGaranTlAccountId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongInternalBankAccountsWithIban() throws JSONException, IOException {
        this.initializeTest();
        String path = "internal/iban";
        InternalTransferWithIbanRequest request = new InternalTransferWithIbanRequest();
        request.setAmount(100);
        request.setIban(gzlIban);
        request.setSenderAccountId(vhpGaranTlAccountId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongInternalBankAccountsWithAccountNo() throws JSONException, IOException {
        this.initializeTest();
        String path = "internal/account";
        InternalTransferWithAccountNoTransferRequest request = new InternalTransferWithAccountNoTransferRequest();
        request.setAmount(100);
        request.setAccountNo(gzlAccount);
        request.setBic(isBankBic);
        request.setSenderAccountId(vhpGaranTlAccountId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        System.out.println(js.toString());
        DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongExterNalBankAccountsWithIban() throws JSONException, IOException {
        this.initializeTest();
        String path = "external/iban";
        ExternalTransferWithIbanRequest request = new ExternalTransferWithIbanRequest();
        request.setAmount(100);
        request.setIban(esmaIban);
        request.setSenderAccountId(vhpGaranTlAccountId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);
    }

    @Test
    public void transferAmongExterNalBankAccountsWithAccountNo() throws JSONException, IOException {
        this.initializeTest();
        String path = "external/account";
        ExternalTransferWithAccountNoRequest request = new ExternalTransferWithAccountNoRequest();
        request.setAmount(100);
        request.setAccountNo(emreAccount);
        request.setBic(isBankBic);
        request.setSenderAccountId(vhpGaranTlAccountId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));
        DataSource.clearAllList();
        Assert.assertTrue(js.getLong("id") > 0);

    }


    @Test
    public void get() throws Exception {
        this.initializeTest();
        String path = "internal/among";
        AmongTransferItsAccountsRequest request = new AmongTransferItsAccountsRequest();
        request.setAmount(100);
        request.setReceiverAccountId(vhpGaranTlAccountId);
        request.setSenderAccountId(vhpGaranTlAccountDepId);
        String content = json(request);
        ClientResponse resp = webService.path("api").path("transfers/" + path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        JSONObject js = new JSONObject(resp.getEntity(String.class));

        ClientResponse respGet = webService.path("api").path("transactions/" + js.getLong("id")).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        JSONObject getById = new JSONObject(respGet.getEntity(String.class));
        DataSource.clearAllList();
        Assert.assertNotNull(getById);

    }


    @Test
    public void getAll() throws Exception {
        ClientResponse resp = webService.path("api").path("transactions").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        String actual = resp.getEntity(String.class);
        JSONArray jsonArray = new JSONArray(actual);
        Assert.assertTrue(jsonArray.length() > 0);
    }

}
