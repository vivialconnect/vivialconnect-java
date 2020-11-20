package net.vivialconnect.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import net.vivialconnect.model.account.Transaction;
import net.vivialconnect.model.account.TransactionType;
import net.vivialconnect.tests.data.DataSource;
import org.junit.Test;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.error.VivialConnectException;

public class AccountTest extends BaseTestCase {

    private DataSource ds = getDataSource();

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

    @Test
    public void test_get_transactions() throws VivialConnectException {
        Account account = getAccount();
        List<Transaction> transactions = ds.getTransactions(account, "2000-01-19T04:00:00Z", "2099-12-31T00:59:59Z", 1, 50);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
    }


    @Test
    public void test_get_transactions_with_page_and_limit() throws VivialConnectException{
        Account account = getAccount();
        List<Transaction> transactions = ds.getTransactions(account,"2000-01-19T04:00:00Z", "2099-12-31T00:59:59Z",1,5);

        assertNotNull(transactions);
        assertEquals(5, transactions.size());
    }

    @Test
    public void test_get_transactions_by_type() throws VivialConnectException {
        TransactionType transactionType = TransactionType.SMS_LOCAL_OUT;
        Account account = getAccount();
        List<Transaction> transactions = ds.getTransactions(account,"2000-01-19T04:00:00Z", "2099-12-31T00:59:59Z", transactionType);

        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        String transactionTypeName = transactionType.name().toLowerCase();

        for(Transaction t : transactions){
            assertEquals(transactionTypeName, t.getTransactionType());
        }
    }

    @Test
    public void test_get_transactions_by_type_with_pages_and_limit() throws VivialConnectException{

        TransactionType transactionType = TransactionType.SMS_LOCAL_OUT;
        Account account = getAccount();
        List<Transaction> transactions = ds.getTransactions(account,"2000-01-19T04:00:00Z", "2099-12-31T00:59:59Z", transactionType,1, 10);

        assertNotNull(transactions);
        assertTrue(transactions.size() <= 10);

        String transactionTypeName = transactionType.name().toLowerCase();

        for(Transaction t : transactions){
            assertEquals(transactionTypeName, t.getTransactionType());
        }

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