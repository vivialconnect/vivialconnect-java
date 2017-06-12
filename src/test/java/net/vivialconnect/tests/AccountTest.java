package net.vivialconnect.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.VivialConnectException;

public class AccountTest extends BaseTestCase {

    @Test
    public void test_get_account() throws VivialConnectException {
        Account account = getAccount();

        assertTrue(account.getId() > 0);
    }

    @Test
    public void test_update_account() throws VivialConnectException {
        Account account = getAccount();
        Date modifiedDateBeforeUpdate = account.getDateModified();

        String newCompanyName = getNewCompanyName(account);
        account.setCompanyName(newCompanyName);

        updateAccount(account);

        assertEquals(newCompanyName, account.getCompanyName());
        assertTrue(account.getDateModified().getTime() > modifiedDateBeforeUpdate.getTime());
    }

    private String getNewCompanyName(Account account) {
        String testAccountCompanyName = account.getCompanyName();
        if (testAccountCompanyName.equals("Vivial Connect")) {
            return "Newtech SRL";
        } else {
            return "Vivial Connect";
        }
    }

    private Account getAccount() throws VivialConnectException {
        return getDataSource().getAccount();
    }

    private void updateAccount(Account account) throws VivialConnectException {
        getDataSource().updateAccount(account);
    }
}