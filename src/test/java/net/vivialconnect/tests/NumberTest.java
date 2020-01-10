package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vivialconnect.tests.data.DataSource;
import org.junit.Ignore;
import org.junit.Test;

import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;

public class NumberTest extends BaseTestCase {

    private static final String buyTestRegion = "SC";

    @Test
    public void test_get_associated_numbers() throws VivialConnectException {
        assertTrue(getDataSource().getAssociatedNumbers().size() > 0);
    }

    @Test
    public void test_get_associated_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getDataSource().getNumberById(getDataSource().getAssociatedNumbers().get(0).getId());

        assertTrue(associatedNumber.getId() > 0);
    }

    @Test
    public void test_get_local_associated_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getDataSource().getNumberById(getDataSource().getLocalAssociatedNumbers().get(0).getId());

        assertTrue(associatedNumber.getId() > 0);
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_associated_number_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        getDataSource().getNumberById(0);
    }

    /* @Test
    public void test_number_not_found() throws VivialConnectException {
        assertNull(getNumberById(1));
    } */

    @Test
    public void test_find_available_numbers_in_region_with_limit() throws VivialConnectException {
        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersInRegion(
                "MN", withLimitOf(2));

        if (availableNumbers.size() > 0) {
            assertEquals(2, availableNumbers.size());
        }
    }

    @Test
    public void test_find_available_numbers_by_area_code_with_limit() throws VivialConnectException {
        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersByAreaCode("302", withLimitOf(2));

        if (availableNumbers.size() > 0) {
            assertEquals(2, availableNumbers.size());
        }
    }

    @Test
    public void test_find_available_numbers_by_postal_code_with_limit() throws VivialConnectException {
        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersByPostalCode(
                "55404", withLimitOf(2));

        if (availableNumbers.size() > 0) {
            assertEquals(2, availableNumbers.size());
        }
    }

    @Test
    public void test_get_local_associated_numbers() throws VivialConnectException {
        assertEquals("local", getDataSource().getLocalAssociatedNumbers().get(0).getPhoneNumberType());
    }

    @Test
    public void test_number_count() throws VivialConnectException {
        assertEquals(getDataSource().getAssociatedNumbers().size(), getDataSource().numberCount());
    }

    @Test
    public void test_number_count_local() throws VivialConnectException {
        assertEquals(getDataSource().getLocalAssociatedNumbers().size(), getDataSource().numberCountLocal());
    }

    @Test
    public void test_buy_available_number() throws VivialConnectException {
        int numbersToTry = 5;
        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersInRegion(
                buyTestRegion, withLimitOf(numbersToTry));

        if (availableNumbers.size() > 0) {
            assertEquals(numbersToTry, availableNumbers.size());
        }

        AvailableNumber availableNumber = null;
        AssociatedNumber boughtNumber = null;
        int numIndex = 0;

        do { // Try multiple numbers due to the BW bug causing unavailable numbers to show up as available
            availableNumber = availableNumbers.get(numIndex);
            boughtNumber = getDataSource().buyAvailable(availableNumber);
            numIndex++;
        } while (boughtNumber == null && numIndex < numbersToTry);

        assertNotNull(boughtNumber);
        assertEquals(availableNumber.getPhoneNumber(), boughtNumber.getPhoneNumber());
        assertTrue(getDataSource().delete(boughtNumber));
    }

    @Test
    public void test_buy_number() throws VivialConnectException {
        int numbersToTry = 5;
        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersInRegion(
                buyTestRegion, withLimitOf(numbersToTry));

        if (availableNumbers.size() > 0) {
            assertEquals(numbersToTry, availableNumbers.size());
        }

        AvailableNumber availableNumber = null;
        AssociatedNumber boughtNumber = null;
        int numIndex = 0;

        do { // Try multiple numbers due to the BW bug causing unavailable numbers to show up as available
            availableNumber = availableNumbers.get(numIndex);
            String areaCode = availableNumber.getPhoneNumber().substring(2, 5);
            boughtNumber = getDataSource().buy(availableNumber.getPhoneNumber(),
                            areaCode, availableNumber.getPhoneNumberType(), null);
            numIndex++;
        } while (boughtNumber == null && numIndex < numbersToTry);

        assertNotNull(boughtNumber);
        assertEquals(availableNumber.getPhoneNumber(), boughtNumber.getPhoneNumber());
        assertTrue(getDataSource().delete(boughtNumber));
    }

    @Ignore("Purchase phone numbers with area_code is not enabled in the API")
    @Test()
    public void test_buy_local_number() throws VivialConnectException {
        int numbersToTry = 5;

        List<AvailableNumber> availableNumbers = getDataSource().findAvailableNumbersInRegion(
                buyTestRegion, withLimitOf(numbersToTry));

        if (availableNumbers.size() > 0) {
            assertEquals(numbersToTry, availableNumbers.size());
        }

        AvailableNumber availableNumber = null;
        AssociatedNumber boughtNumber = null;
        int numIndex = 0;

        do { // Try multiple numbers due to the BW bug causing unavailable numbers to show up as available
            availableNumber = availableNumbers.get(numIndex);
            String areaCode = availableNumber.getPhoneNumber().substring(2, 5);
            boughtNumber = getDataSource().buyLocalNumber(availableNumber.getPhoneNumber(), areaCode, null);
            numIndex++;
        } while (boughtNumber == null && numIndex < numbersToTry);

        assertNotNull(boughtNumber);
        assertEquals(availableNumber.getPhoneNumber(), boughtNumber.getPhoneNumber());
        assertTrue(getDataSource().deleteLocalNumber(boughtNumber));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_delete_local_number_with_non_local_type_throws_unsupported_operation_exception() throws VivialConnectException {
        AssociatedNumber localNumber = getDataSource().getLocalAssociatedNumbers().get(0);
        localNumber.setPhoneNumberType("tollfree");

        getDataSource().deleteLocalNumber(localNumber);
    }

    @Test
    public void test_update_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getDataSource().getNumberById(getDataSource().getAssociatedNumbers().get(0).getId());
        Date dateModifiedBeforeUpdate = associatedNumber.getDateModified();

        String newIncomingTextUrl = getNewIncomingTextUrl(associatedNumber);
        associatedNumber.setIncomingTextUrl(newIncomingTextUrl);

        getDataSource().updateNumber(associatedNumber);

        assertTrue(associatedNumber.getDateModified().getTime() > dateModifiedBeforeUpdate.getTime());
        assertEquals(newIncomingTextUrl, associatedNumber.getIncomingTextUrl());
    }

    @Test
    public void test_update_local_number() throws VivialConnectException {
        AssociatedNumber associatedNumber = getDataSource().getNumberById(getDataSource().getLocalAssociatedNumbers().get(0).getId());
        Date dateModifiedBeforeUpdate = associatedNumber.getDateModified();

        String newIncomingTextUrl = getNewIncomingTextUrl(associatedNumber);
        associatedNumber.setIncomingTextUrl(newIncomingTextUrl);

        getDataSource().updateLocalNumber(associatedNumber);

        assertTrue(associatedNumber.getDateModified().getTime() > dateModifiedBeforeUpdate.getTime());
        assertEquals(newIncomingTextUrl, associatedNumber.getIncomingTextUrl());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_update_local_number_with_non_local_type_throws_unsupported_operation_exception() throws VivialConnectException {
        AssociatedNumber associatedNumber = getDataSource().getNumberById(getDataSource().getLocalAssociatedNumbers().get(0).getId());
        associatedNumber.setPhoneNumberType("tollfree");

        getDataSource().updateLocalNumber(associatedNumber);
    }

    private String getNewIncomingTextUrl(AssociatedNumber associatedNumber) {
        if ("https://foo.bar/callback".equals(associatedNumber.getIncomingTextUrl())) {
            return "https://bar.foo/callback";
        } else {
            return "https://foo.bar/callback";
        }
    }

    @Test
    public void test_number_lookup() throws VivialConnectException {
        AssociatedNumber number = getDataSource().getNumberById(getDataSource().getAssociatedNumbers().get(0).getId());
        assertEquals(number.getPhoneNumber().substring(1), getDataSource().numberLookup(number).getPhoneNumber());
    }

    @Test
    public void test_get_available_local_numbers_with_valid_param() throws VivialConnectException {

        Map<String, String> params = new HashMap<String, String>();

        DataSource dataSource = getDataSource();

        try {
            dataSource.findAvailableNumbersByAreaCode("error", params);
        } catch (VivialConnectException e) {
            assertEquals("Error fetching available numbers", e.getMessage());
            assertEquals(0, e.getErrorCode());
        }
    }

}