package net.vivialconnect.tests;

import static org.junit.Assert.*;

import java.lang.Integer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;

import org.junit.Test;

import net.vivialconnect.model.user.User;
import net.vivialconnect.model.error.VivialConnectException;

public class UserTest extends BaseTestCase {

    @Test
    public void test_get_users() throws VivialConnectException {
        List<User> users = getDataSource().getUsers();
        assertTrue(users != null);
    }

    @Test
    public void test_get_user_by_id() throws VivialConnectException {
        List<User> users = getDataSource().getUsers();
        User user = users.get(0);
        User retrievedUser = getDataSource().getUserById(user.getId());
        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void test_get_user_count() throws VivialConnectException, InterruptedException {
        List<User> users = getDataSource().getUsers();
        int userCount = getDataSource().userCount();
        assertEquals(users.size(), userCount);

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_create_and_delete_user() throws VivialConnectException {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("account_id", 1);
        attributes.put("username", "someUser");
        attributes.put("first_name", "Jake");
        attributes.put("last_name", "User");
        attributes.put("email", "test@user2.com");
        attributes.put("password", "moosetalk470");

        User newUser = getDataSource().createUser(attributes);

        assertEquals(newUser.getAccountId(), 1);
        assertEquals(newUser.getEmail(), "test@user2.com");

        assertTrue(getDataSource().deleteUser(newUser));
    }

    // Disabled until User.changePassword works

    // @Test
    // public void test_update_user_password() throws VivialConnectException {
    //     List<User> users = getDataSource().getUsers();
    //     User user = users.get(0);

    //     boolean passwordChanged;

    //     passwordChanged = getDataSource().updateUserPassword(user, "moosetalk470", "sO4_p12Qan");
    //     assertTrue(passwordChanged);
    //     // Change back
    //     passwordChanged = getDataSource().updateUserPassword(user, "sO4_p12Qan", "moosetalk470");
    //     assertTrue(passwordChanged);
    // }
}