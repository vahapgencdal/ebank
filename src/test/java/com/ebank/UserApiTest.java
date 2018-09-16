package com.ebank;

import com.ebank.conf.ClientProvider;
import com.ebank.conf.ServerProvider;
import com.ebank.model.entity.User;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class UserApiTest {

    private final ServerProvider serverProvider;
    private final ClientProvider clientProvider;
    private WebResource webService;

    public UserApiTest() {
        serverProvider = new ServerProvider();
        clientProvider = new ClientProvider(serverProvider);
    }

    @Before
    public void startServer() throws IOException {
        serverProvider.createServer();
        webService = clientProvider.getWebResource();
    }

    @After
    public void stopServer() {
        serverProvider.stop();
    }


    @Test
    public void testGetAllUsersShouldReturnSuccessStatus() throws IOException {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);

        assertEquals(200, resp.getStatus());
    }

    @Test
    public void testGetAllUsersShouldReturnJSArray() throws IOException {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);

        assertTrue("Result must be a Json array: But it starts with '{'!", !actual.startsWith("{"));
        assertTrue("Result must be a Json array: But it does not start with '['!", actual.startsWith("["));
    }

    @Test
    public void testGetAllUsersShouldReturnUsers() throws IOException {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);

        String expectedUser1 = "{\"id\":1,\"firstName\":\"Vahap1\",\"lastName\":\"Genc1\"}";
        String expectedUser10 = "{\"id\":10,\"firstName\":\"Vahap10\",\"lastName\":\"Genc10\"}";

        assertTrue(actual.contains(expectedUser1));
        assertTrue(actual.contains(expectedUser10));
    }

    @Test
    public void testGetUserByIdShouldReturnSuccessStatus() throws IOException {
        ClientResponse resp = webService.path("api").path("users/1")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);

        assertEquals(200, resp.getStatus());
    }

    @Test
    public void testGetUserByIdOneShouldReturnFirstUser() throws IOException {
        ClientResponse resp = webService.path("api").path("users/1")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);
        String expectedUser1 = "{\"id\":1,\"firstName\":\"Vahap1\",\"lastName\":\"Genc1\"}";

        assertTrue(actual.equals(expectedUser1));
    }

    @Test
    public void testCreateUserShouldReturnNewUserWithCorrectId() throws IOException {
        ClientResponse resp = webService.path("api").path("users")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, new User());

        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);
        String expectedId = "\"id\":11";

        assertTrue(actual.contains(expectedId));
    }

    @Test
    public void testUpdateUserShouldReturnUpdatedUser() throws IOException {

        User updateUser = new User();
        updateUser.setId(1);
        updateUser.setFirstName("XX");
        updateUser.setLastName("YY");

        ClientResponse resp = webService.path("api").path("users/1")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, updateUser);

        String actual = resp.getEntity(String.class);
        String expectedId = "\"id\":1";
        String expectedFirstNam = "\"firstName\":\"XX\"";
        String expectedLastNam = "\"lastName\":\"YY\"";

        assertTrue(actual.contains(expectedId));
        assertTrue(actual.contains(expectedFirstNam));
        assertTrue(actual.contains(expectedLastNam));
    }

    @Test
    public void testGetNumberOfUserShouldReturnSuccessStatusAndCorrectNumber() throws IOException {

        String actual = getNumberOfUsers();

        String expectedNumberOfUsers = "10";
        assertTrue(actual.equals(expectedNumberOfUsers));
    }

    private String getNumberOfUsers() {
        ClientResponse resp = webService.path("api").path("users/numberOfUsers")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        System.out.println("Got Response: " + resp);
        assertEquals(200, resp.getStatus());
        return resp.getEntity(String.class);
    }

    @Test
    public void testRemoveUserShouldReturnSuccessStatus() throws IOException {

        ClientResponse resp = webService.path("api").path("users/1")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);

        System.out.println("Got Response: " + resp);
        assertEquals(204, resp.getStatus());
    }

    @Test
    public void testRemoveUserShouldDecreaseNumberOfUsersByOne() throws IOException {

        int numberOfUsersBefore = Integer.parseInt(getNumberOfUsers());

        webService.path("api").path("users/1")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);

        int numberOfUsersAfter = Integer.parseInt(getNumberOfUsers());

        assertTrue(numberOfUsersAfter == numberOfUsersBefore - 1);
    }
}
