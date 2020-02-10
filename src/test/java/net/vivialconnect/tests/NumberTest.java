package net.vivialconnect.tests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.vivialconnect.model.enums.CallbackMethod;
import net.vivialconnect.model.number.*;
import net.vivialconnect.model.number.Number;
import net.vivialconnect.tests.data.DataSource;
import org.junit.Ignore;
import org.junit.Test;
import net.vivialconnect.model.error.VivialConnectException;
import static org.junit.Assert.*;

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
    public void test_tags_update() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        AssociatedNumber number = dataSource.getLocalAssociatedNumbers().get(0);
        Map<String, String> testTags = new HashMap<String, String>();

        for (int i = 1; i < 3; i++) {
            testTags.put(String.format("test%d", i), String.format("tag%d", i));
        }

        testTags.put("empty_tag", "");

        TagCollection tagCollection = dataSource.updateTags(testTags, number);
        Map<String, String> tags = tagCollection.getTags();

        assertEquals(tags.get("test1"), "tag1");
        assertEquals(tags.get("test2"), "tag2");
        assertEquals(tags.get("empty_tag"), "");

    }

    @Test(expected = VivialConnectException.class)
    public void test_invalid_tags_update_quantity() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);
        Map<String, String> testTags = new HashMap<String, String>();

        for (int i = 1; i < 9; i++) {
            testTags.put(String.format("test%d", i), String.format("tag%d", i));
        }

        testTags.put("testtag", "invalid");

        dataSource.updateTags(testTags, associatedNumber);

    }

    @Test(expected = VivialConnectException.class)
    public void test_tag_with_invalid_key_length() throws VivialConnectException {

        DataSource dataSource = getDataSource();
        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);

        Map<String, String> invalidTags = new HashMap<String, String>();
        invalidTags.put("UnnecesaryVeryLongTagName", "Something...");
        invalidTags.put("testtag", "invalid");

        TagCollection invalidTag = new TagCollection();
        invalidTag.setTags(invalidTags);

        dataSource.updateTags(invalidTags, associatedNumber);

    }

    @Test(expected = VivialConnectException.class)
    public void test_tags_with_invalid_value_length() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);
        String longTagValue = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                "Pellentesque commodo elit eget magna luctus, ullamcorper iaculis sapien pharetra." +
                " Quisque faucibus, urna id fringilla cursus, tellus eros rutrum purus, sit amet fringilla augue nulla vel massa." +
                " Donec in tortor commodo ipsum efficitur volutpat quis a nisl." +
                " Quisque mattis ante sed sapien laoreet molestie. Praesent varius tortor at enim tincidunt lobortis ut at urna.";
        Map<String, String> invalidTagContent = new HashMap<String, String>();
        invalidTagContent.put("tag_key", longTagValue);
        invalidTagContent.put("testtag", "invalid");

        dataSource.updateTags(invalidTagContent, associatedNumber);
    }

    @Test
    public void test_retrieve_all_tags() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("tags", "test_tag");

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);

        dataSource.updateTags(tags, associatedNumber);

        TaggedNumberCollection taggedNumbers = dataSource.getTaggedNumbers(null);

        assertTrue(taggedNumbers.getNumbers().size() > 0);
        assertTrue(taggedNumbers.getCount() >= 1);
        assertTrue(taggedNumbers.getPages() >= 1);

        Number number = (Number) associatedNumber;

        assertTrue(taggedNumbers.getNumbers().contains(number));

    }

    @Test
    public void test_retrieve_all_tags_with_contains_param() throws VivialConnectException {

        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("ddHHmmssSSS");
        String tagValue = simpleDateFormatter.format(new Date());
        String tagKey = "param_tag";
        String emptyTagKey = "empty_tag";
        String emptyTagValue = "";

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);

        Map<String, String> tags = new HashMap<String, String>();
        tags.put(tagKey, tagValue);
        tags.put(emptyTagKey, emptyTagValue);

        dataSource.updateTags(tags, associatedNumber);

        Map<String, String> searchParams = new HashMap<String, String>();
        searchParams.put("contains", tagKey + ":" + tagValue);

        TaggedNumberCollection taggedNumbers = dataSource.getTaggedNumbers(searchParams);

        assertTrue(taggedNumbers.getNumbers().size() >= 1);
        assertTrue(taggedNumbers.getCount() >= 1);
        assertTrue(taggedNumbers.getPages() >= 1);

        Number number = (Number) associatedNumber;

        assertTrue(taggedNumbers.getNumbers().contains(number));

        searchParams.clear();
        searchParams.put("contains", emptyTagKey + ":" + emptyTagValue);

        taggedNumbers = dataSource.getTaggedNumbers(searchParams);

        assertTrue(taggedNumbers.getNumbers().contains(number));

    }

    @Test
    public void test_retrieve_all_tags_with_not_contains_param() throws VivialConnectException {

        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("ddHHmmssSSS");
        String tagValue = simpleDateFormatter.format(new Date());
        String tagKey = "param_tag";

        Map<String, String> tags = new HashMap<String, String>();
        tags.put(tagKey, tagValue);

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);
        dataSource.updateTags(tags, associatedNumber);

        Map<String, String> searchParams = new HashMap<String, String>();
        searchParams.put("notcontains", tagKey + ":" + tagValue);

        TaggedNumberCollection taggedNumbers = dataSource.getTaggedNumbers(searchParams);

        assertTrue(taggedNumbers.getNumbers().size() >= 1);

        Number number = (Number) associatedNumber;

        assertFalse(taggedNumbers.getNumbers().contains(number));
    }

    @Test
    public void test_retrieve_number_tags() throws VivialConnectException {

        String tagKey = "test_tag";
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("ddHHmmssSSS");
        String tagValue = simpleDateFormatter.format(new Date());

        Map<String, String> tags = new HashMap<String, String>();
        tags.put(tagKey, tagValue);

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(0);

        dataSource.updateTags(tags, associatedNumber);

        TagCollection numberTags = dataSource.fetchTags(associatedNumber);

        assertTrue(numberTags.getTags().containsKey(tagKey));
        assertTrue(numberTags.getTags().containsValue(tagValue));

        Number number = (Number) associatedNumber;

        assertTrue(number.getTags().containsKey(tagKey));
        assertTrue(number.getTags().containsValue(tagValue));

    }

    @Test
    public void test_delete_number_tags() throws VivialConnectException {

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("store_id", "500");
        tags.put("location", "US");
        tags.put("platform", "java");

        DataSource dataSource = getDataSource();

        AssociatedNumber associatedNumber = dataSource.getLocalAssociatedNumbers().get(1);
        TagCollection updatedTags = dataSource.updateTags(tags, associatedNumber);

        for (String tagKey : tags.keySet()) {
            String tagValue = tags.get(tagKey);
            assertTrue(updatedTags.getTags().containsKey(tagKey));
            assertTrue(updatedTags.getTags().containsValue(tagValue));
        }

        Map<String, String> tagsToDelete = new HashMap<String, String>();
        tagsToDelete.put("store_id", "");
        tagsToDelete.put("platform", "");

        TagCollection tagsLeft = dataSource.deleteTags(tagsToDelete, associatedNumber);

        for (String tagKey : tagsToDelete.keySet()) {
            assertFalse(tagsLeft.getTags().containsKey(tagKey));
        }

        Number number = (Number) associatedNumber;

        for (String tagKey : tagsToDelete.keySet()) {
            assertFalse(number.getTags().containsKey(tagKey));
        }
    }


    @Test
    public void test_number_callbacks() throws VivialConnectException {

        DataSource dataSource = getDataSource();
        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        AssociatedNumber associatedNumber = associatedNumbers.get(0);

        Number number = (Number) associatedNumber;

        number.setIncomingTextFallbackMethod(CallbackMethod.GET);
        number.setIncomingTextFallbackUrl("http://www.sample.com/callback/fallback");

        number.setIncomingTextMethod(CallbackMethod.POST);
        number.setIncomingTextUrl("http://www.sample.com/callback/incoming");

        number.setStatusTextUrl("http://www.sample.com/callback/status");

        dataSource.updateNumber(number);

        AssociatedNumber updatedNumber = dataSource.getNumberById(associatedNumber.getId());

        assertEquals(updatedNumber.getIncomingTextMethod(), associatedNumber.getIncomingTextMethod());
        assertEquals(updatedNumber.getIncomingTextFallbackMethod(), associatedNumber.getIncomingTextFallbackMethod());

    }


}