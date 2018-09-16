package com.ebank;

import com.ebank.model.entity.User;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 16.09.2018
 * @description :Allow Cor Requests
 */
public class UserApiTest extends BaseApiTest {

    public UserApiTest() {
        super();
    }

    @Test
    public void testGetAllUsersShouldReturnSuccessStatus() {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);

        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testGetAllUsersShouldReturnJSArray() {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);

        Assert.assertTrue("Result must be a Json array: But it starts with '{'!", !actual.startsWith("{"));
        Assert.assertTrue("Result must be a Json array: But it does not start with '['!", actual.startsWith("["));
    }

    @Test
    public void testGetAllUsersShouldReturnUsers() {
        ClientResponse resp = webService.path("api").path("users")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);

        String expectedUser1 = "\"id\":1";
        String expectedUser10 = "\"id\":10";

        Assert.assertTrue(actual.contains(expectedUser1));
        Assert.assertTrue(actual.contains(expectedUser10));
    }

    @Test
    public void testGetUserByIdShouldReturnSuccessStatus() {
        ClientResponse resp = webService.path("api").path("users/1")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);

        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testGetUserByIdOneShouldReturnFirstUser() {
        ClientResponse resp = webService.path("api").path("users/1")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);
        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);
        String expectedUser1 = "\"id\":1";

        Assert.assertTrue(actual.contains(expectedUser1));
    }

    @Test
    public void testCreateUserShouldReturnNewUserWithCorrectId() {
        ClientResponse resp = webService.path("api").path("users")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, new User());

        System.out.println("Got Response: " + resp);
        String actual = resp.getEntity(String.class);
        String expectedId = "\"id\":11";

        Assert.assertTrue(actual.contains(expectedId));
    }

    @Test
    public void testUpdateUserShouldReturnUpdatedUser() {

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
        String expectedFirstName = "\"firstName\":\"XX\"";
        String expectedLastName = "\"lastName\":\"YY\"";

        Assert.assertTrue(actual.contains(expectedId));
        Assert.assertTrue(actual.contains(expectedFirstName));
        Assert.assertTrue(actual.contains(expectedLastName));
    }

    @Test
    public void testGetNumberOfUserShouldReturnSuccessStatusAndCorrectNumber() {

        String actual = getNumberOfUsers();

        String expectedNumberOfUsers = "10";
        Assert.assertEquals(expectedNumberOfUsers, actual);
    }

    private String getNumberOfUsers() {
        ClientResponse resp = webService.path("api").path("users/size")
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        System.out.println("Got Response: " + resp);
        Assert.assertEquals(200, resp.getStatus());
        return resp.getEntity(String.class);
    }

    @Test
    public void testRemoveUserShouldReturnSuccessStatus() {

        ClientResponse resp = webService.path("api").path("users/1")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);

        System.out.println("Got Response: " + resp);
        Assert.assertEquals(204, resp.getStatus());
    }

    @Test
    public void testRemoveUserShouldDecreaseNumberOfUsersByOne() {

        int numberOfUsersBefore = Integer.parseInt(getNumberOfUsers());

        webService.path("api").path("users/1")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);

        int numberOfUsersAfter = Integer.parseInt(getNumberOfUsers());

        Assert.assertEquals(numberOfUsersAfter, numberOfUsersBefore - 1);
    }
}
