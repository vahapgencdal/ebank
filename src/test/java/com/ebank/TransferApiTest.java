package com.ebank;

import com.ebank.mock.AccountMockCreater;
import com.ebank.model.entity.Account;
import com.ebank.model.entity.UserAccount;
import com.ebank.model.request.AccountToAccountRequest;
import com.ebank.model.request.AccountToIbanRequest;
import com.ebank.model.request.IbanToAccountRequest;
import com.ebank.model.request.IbanToIbanRequest;
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

        emreIsBankTlAccount = AccountMockCreater.getUserAccount(UserEnum.EMRE.getVal(), CurrencyEnum.TL.toString(), AccountTypeEnum.DRAW.toString(), "Emre Tl Drawing Account", BankEnum.IS.getVal(), true, 1000000);
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

    private JSONObject complete() throws JSONException {
        ClientResponse respGet = webService.path("api").path("transactions/complete").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        return new JSONObject(respGet.getEntity(String.class));
    }

    private JSONObject transferAccountToAccount(UserAccount sender, UserAccount receiver, double amount, String path) throws IOException, JSONException {
        AccountToAccountRequest request = new AccountToAccountRequest();
        request.setAmount(amount);
        request.setReceiverAccountNo(receiver.getAccountNo());
        request.setReceiverBic(receiver.getBank());
        request.setSenderAccountNo(sender.getAccountNo());
        request.setSenderBic(sender.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path(path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        complete();
        return new JSONObject(resp.getEntity(String.class));
    }

    @Test
    public void AccountsToAccountSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        transferAccountToAccount(vhpGaranTlAccountSec, vhpGaranTlAccount, 100, "transfers/accountToAccount");
        JSONObject account = getAccount(vhpGaranTlAccountSec.getAccountNo(), vhpGaranTlAccountSec.getBank(), "users");
        boolean result = vhpGaranTlAccountSec.getTotalAmount() - 100 == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToAccountSameUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, vhpGaranTlAccount, 300, "transfers/accountToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToAccountDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(emreGaranTlAccount, vhpGaranTlAccount, 200, "transfers/accountToAccount");
        JSONObject account = getAccount(emreGaranTlAccount.getAccountNo(), emreGaranTlAccount.getBank(), "users");
        double resultAmount = emreGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToAccountDifferentUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 400, "transfers/accountToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(emreIsBankTlAccount, vhpGaranTlAccount, 100, "transfers/accountToAccount");
        JSONObject account = getAccount(emreIsBankTlAccount.getAccountNo(), emreIsBankTlAccount.getBank(), "users");
        double resultAmount = emreIsBankTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, emreIsBankTlAccount, 100, "transfers/accountToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    //ERROR CASES NOT ENOUGH MONEY

    @Test
    public void AccountsToAccountSameUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject js = transferAccountToAccount(vhpGaranTlAccountSec, vhpGaranTlAccount, 100000000, "transfers/accountToAccount");
        Assert.assertEquals("NOK", js.getString("code"));
    }

    @Test
    public void AccountsToAccountSameUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, vhpGaranTlAccount, 100000000, "transfers/accountToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void AccountsToAccountDifferentUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(emreGaranTlAccount, vhpGaranTlAccount, 20000000, "transfers/accountToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void AccountsToAccountDifferentUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 200000000, "transfers/accountToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(emreIsBankTlAccount, vhpGaranTlAccount, 1042342340, "transfers/accountToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void AccountsToAccountDifferentUserDifferentBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToAccount(vhpGaranUsdAccount, emreIsBankTlAccount, 1543344400, "transfers/accountToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }


    private JSONObject transferAccountToIban(UserAccount sender, UserAccount receiver, double amount, String path) throws IOException, JSONException {
        AccountToIbanRequest request = new AccountToIbanRequest();
        request.setAmount(amount);
        request.setReceiverIban(receiver.getIban());
        request.setSenderAccountNo(sender.getAccountNo());
        request.setSenderBic(sender.getBank());
        String content = json(request);
        ClientResponse resp = webService.path("api").path(path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        complete();
        return new JSONObject(resp.getEntity(String.class));
    }

    @Test
    public void AccountsToIbanSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, vhpGaranTlAccountSec, 100, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranTlAccount.getAccountNo(), vhpGaranTlAccount.getBank(), "users");
        double resultAmount = vhpGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToIbanSameUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, vhpGaranTlAccount, 500, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToIbanDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, emreGaranTlAccount, 200, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranTlAccount.getAccountNo(), vhpGaranTlAccount.getBank(), "users");
        double resultAmount = vhpGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToIbanDifferentUserSameBankkDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, emreGaranTlAccount, 400, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }


    @Test
    public void AccountsToIbanDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, emreIsBankTlAccount, 400, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranTlAccount.getAccountNo(), vhpGaranTlAccount.getBank(), "users");
        double resultAmount = vhpGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void AccountsToIbanDifferentUserDifferentBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, emreIsBankTlAccount, 200, "transfers/accountToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    //ERROR CASE NOT ENOUGH MONEY

    @Test
    public void AccountsToIbanSameUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, vhpGaranTlAccountSec, 100000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void AccountsToIbanSameUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, vhpGaranTlAccount, 500000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void AccountsToIbanDifferentUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, emreGaranTlAccount, 200000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void AccountsToIbanDifferentUserSameBankkDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, emreGaranTlAccount, 400000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }


    @Test
    public void AccountsToIbanDifferentUserDifferentBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranTlAccount, emreIsBankTlAccount, 400000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void AccountsToIbanDifferentUserDifferentBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferAccountToIban(vhpGaranUsdAccount, emreIsBankTlAccount, 2000000000, "transfers/accountToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }


    private JSONObject transferIbanToIban(UserAccount sender, UserAccount receiver, double amount, String path) throws IOException, JSONException {
        IbanToIbanRequest request = new IbanToIbanRequest();
        request.setAmount(amount);
        request.setReceiverIban(receiver.getIban());
        request.setSenderIban(sender.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path(path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        complete();
        return new JSONObject(resp.getEntity(String.class));
    }

    @Test
    public void IbanToIbanSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranTlAccountSec, vhpGaranTlAccount, 200, "transfers/ibanToIban");
        JSONObject account = getAccount(vhpGaranTlAccountSec.getAccountNo(), vhpGaranTlAccountSec.getBank(), "users");
        double resultAmount = vhpGaranTlAccountSec.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToIbanSameUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, vhpGaranTlAccount, 100, "transfers/ibanToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToIbanDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(emreIsBankTlAccount, vhpIsBankTlAccount, 100, "transfers/ibanToIban");
        JSONObject account = getAccount(emreIsBankTlAccount.getAccountNo(), emreIsBankTlAccount.getBank(), "users");
        double resultAmount = emreIsBankTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToIbanDifferentUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, emreGaranTlAccount, 400, "transfers/ibanToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToIbanDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(emreGaranTlAccount, vhpIsBankTlAccount, 500, "transfers/ibanToIban");
        JSONObject account = getAccount(emreGaranTlAccount.getAccountNo(), emreGaranTlAccount.getBank(), "users");
        double resultAmount = emreGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToIbanDifferentUserDifferentBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, emreIsBankTlAccount, 200, "transfers/ibanToIban");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }


    //ERROR CASE FOR NOT ENOUGH MONEY

    @Test
    public void IbanToIbanSameUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranTlAccountSec, vhpGaranTlAccount, 20000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void IbanToIbanSameUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, vhpGaranTlAccount, 100000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToIbanDifferentUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(emreIsBankTlAccount, vhpIsBankTlAccount, 100000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToIbanDifferentUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, emreGaranTlAccount, 400000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToIbanDifferentUserDifferentBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(emreGaranTlAccount, vhpIsBankTlAccount, 400000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToIbanDifferentUserDifferentBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToIban(vhpGaranUsdAccount, emreIsBankTlAccount, 200000000, "transfers/ibanToIban");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    private JSONObject transferIbanToAccount(UserAccount sender, UserAccount receiver, double amount, String path) throws IOException, JSONException {
        IbanToAccountRequest request = new IbanToAccountRequest();
        request.setAmount(amount);
        request.setReceiverAccountNo(receiver.getAccountNo());
        request.setReceiverBic(receiver.getBank());
        request.setSenderIban(sender.getIban());
        String content = json(request);
        ClientResponse resp = webService.path("api").path(path).type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, content);
        complete();
        return new JSONObject(resp.getEntity(String.class));
    }


    @Test
    public void IbanToAccountSameUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranTlAccount, vhpGaranTlAccountSec, 100, "transfers/ibanToAccount");

        JSONObject account = getAccount(vhpGaranTlAccount.getAccountNo(), vhpGaranTlAccount.getBank(), "users");
        double resultAmount = vhpGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToAccountSameUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 100, "transfers/ibanToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToAccountDifferentUserSameBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(emreGaranTlAccount, vhpGaranTlAccount, 200, "transfers/ibanToAccount");
        JSONObject account = getAccount(emreGaranTlAccount.getAccountNo(), emreGaranTlAccount.getBank(), "users");
        double resultAmount = emreGaranTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToAccountDifferentUserSameBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 400, "transfers/ibanToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToAccountDifferentUserDifferentBank() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(emreIsBankTlAccount, vhpGaranTlAccount, 300, "transfers/ibanToAccount");
        JSONObject account = getAccount(emreIsBankTlAccount.getAccountNo(), emreIsBankTlAccount.getBank(), "users");
        double resultAmount = emreIsBankTlAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount") - transaction.getJSONObject("data").getDouble("amount") * transaction.getJSONObject("data").getDouble("fee");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    @Test
    public void IbanToAccountDifferentUserDifferentBankDifferentCurrency() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreIsBankTlAccount, 200, "transfers/ibanToAccount");
        JSONObject account = getAccount(vhpGaranUsdAccount.getAccountNo(), vhpGaranUsdAccount.getBank(), "users");
        double resultAmount = vhpGaranUsdAccount.getTotalAmount() - transaction.getJSONObject("data").getDouble("amount");
        boolean result = resultAmount == account.getJSONObject("data").getDouble("totalAmount");
        Assert.assertTrue(result);
    }

    //ERROR CASE FOR NOT ENOUGH MONEY
    @Test
    public void IbanToAccountSameUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranTlAccount, vhpGaranTlAccountSec, 100000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));
    }

    @Test
    public void IbanToAccountSameUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 100000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToAccountDifferentUserSameBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(emreGaranTlAccount, vhpGaranTlAccount, 200000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToAccountDifferentUserSameBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreGaranTlAccount, 40000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToAccountDifferentUserDifferentBankNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(emreIsBankTlAccount, vhpGaranTlAccount, 30000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }

    @Test
    public void IbanToAccountDifferentUserDifferentBankDifferentCurrencyNotEnoughMoney() throws JSONException, IOException {
        this.initializeTest();
        JSONObject transaction = transferIbanToAccount(vhpGaranUsdAccount, emreIsBankTlAccount, 200000000, "transfers/ibanToAccount");
        Assert.assertEquals("NOK", transaction.getString("code"));

    }
}
