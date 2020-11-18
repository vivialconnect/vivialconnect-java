package net.vivialconnect.tests;

import static org.junit.Assert.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Map;
import java.util.HashMap;
import net.vivialconnect.model.enums.RoleType;
import net.vivialconnect.model.user.Credential;
import net.vivialconnect.model.user.CredentialUpdateField;
import net.vivialconnect.model.user.Role;
import net.vivialconnect.tests.data.DataSource;
import org.junit.Assume;
import org.junit.Test;

import net.vivialconnect.model.user.User;
import net.vivialconnect.model.error.VivialConnectException;

public class UserTest extends BaseTestCase {

    private DataSource ds = getDataSource();

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
    public void test_create_user() throws VivialConnectException {

        List<User> users = getDataSource().getUsers();

        int someUserId = users.get(0).getAccountId();

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("account_id", someUserId);
        attributes.put("username", "someUser");
        attributes.put("first_name", "Jake");
        attributes.put("last_name", "User");
        attributes.put("email", "test@user2.com");
        attributes.put("password", "moosetalk470");
        attributes.put("role", "AccountAdministrator");

        User newUser = getDataSource().createUser(attributes);

        assertEquals(newUser.getAccountId(), someUserId);
        assertEquals(newUser.getEmail(), "test@user2.com");

        assertTrue(getDataSource().deleteUser(newUser));

    }

    public void userRoleTypeBaseTest(RoleType roleType) throws VivialConnectException {

        List<User> users = getDataSource().getUsersByRoleType(roleType);
        Assume.assumeTrue("Users for role type not found: " + roleType.name(), users.size() > 0);

        User user = users.get(0);
        List<Role> roles = user.getRoles();

        for (Role role : roles) {
            if (role.getRoleType() == roleType) {
                assertEquals(roleType, role.getRoleType());
                return;
            }
        }

        fail("Role not found: " + roleType);
    }

    @Test
    public void test_system_role_type() throws VivialConnectException{
        userRoleTypeBaseTest(RoleType.SYSTEM);
    }

    @Test
    public void  test_client_role_type() throws  VivialConnectException{
        userRoleTypeBaseTest(RoleType.CLIENT);
    }

    @Test
    public void test_list_credentials() throws VivialConnectException {
        User testUser = getDataSource().getUsers().get(0);
        List<Credential> userCredentials = ds.getCredentials(testUser);

        assertNotNull(userCredentials);
        assertFalse(userCredentials.isEmpty());

        Credential credential = userCredentials.get(0);

        assertNotNull(credential.getApiKey());
    }

    @Test
    public void test_create_credentials() throws VivialConnectException{

        User user = ds.getUsers().get(0);
        Credential credential = ds.createCredentials(user, "Java SDK Credentials");

        assertNotNull(credential);
        assertNotNull(credential.getApiKey());
        assertNotNull(credential.getApiSecret());
    }

    @Test
    public void test_update_credentials() throws VivialConnectException{
        User user = ds.getUsers().get(0);
        Credential credential = ds.getCredentials(user).get(0);

        Map<CredentialUpdateField, Object> infoToUpdate = new HashMap<CredentialUpdateField, Object>();
        infoToUpdate.put(CredentialUpdateField.NAME, "Test");
        infoToUpdate.put(CredentialUpdateField.ACTIVE, false);

        Credential updatedCredential = ds.updateCredential(user, credential.getId(),  infoToUpdate);

        assertEquals("Test",updatedCredential.getName());
        assertFalse(updatedCredential.isActive());
    }

    @Test
    public void test_count_credentials() throws VivialConnectException{
        User user = ds.getUsers().get(0);
        int count = ds.countCredentials(user);

        assertTrue(count > 0);
    }

    @Test
    public void test_delete_credential() throws VivialConnectException{
        User user = ds.getUsers().get(0);
        Credential credential = ds.createCredentials(user, "Java SDK Credentials");
        boolean wasDeleted = ds.deleteCredential(user, credential.getId());

        assertTrue(wasDeleted);
    }


}