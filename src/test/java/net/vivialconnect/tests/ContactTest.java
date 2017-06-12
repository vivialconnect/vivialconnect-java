package net.vivialconnect.tests;

import static org.junit.Assert.*;

import java.lang.Integer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.error.VivialConnectException;

public class ContactTest extends BaseTestCase {

    @Test
    public void test_create_update_contact() throws VivialConnectException, InterruptedException {
        Contact contact = new Contact();
        contact.setFirstName("John");
        contact.setLastName("Contact");
        contact.setEmail("john.contact.@unittest.com");
        contact.setContactType("main");

        Contact createdContact = getDataSource().createContact(contact);

        assertEquals(createdContact.getFirstName(), "John");
        assertEquals(createdContact.getLastName(), "Contact");
        assertEquals(createdContact.getEmail(), "john.contact.@unittest.com");
        assertEquals(createdContact.getContactType(), "main");

        createdContact.setCity("Anytown");
        Contact updatedContact = getDataSource().updateContact(contact);
        assertEquals(updatedContact.getCity(), "Anytown");

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_get_contacts() throws VivialConnectException {
        List<Contact> contacts = getDataSource().getContacts();
        assertTrue(contacts != null);
    }

    @Test
    public void test_get_contact_by_id() throws VivialConnectException, InterruptedException {
        List<Contact> contacts = getDataSource().getContacts();
        Contact contact = contacts.get(0);
        Contact retrievedContact = getDataSource().getContactById(contact.getId());
        assertEquals(contact.getId(), retrievedContact.getId());
        assertEquals(contact.getEmail(), retrievedContact.getEmail());

        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test_get_contact_count() throws VivialConnectException {
        List<Contact> contacts = getDataSource().getContacts();
        int contactCount = getDataSource().contactCount();
        assertEquals(contacts.size(), contactCount);
    }

    @Test
    public void test_delete_contact() throws VivialConnectException {
        List<Contact> contacts = getDataSource().getContacts();
        Contact contact = contacts.get(0);
        assertTrue(getDataSource().deleteContact(contact));
    }
}