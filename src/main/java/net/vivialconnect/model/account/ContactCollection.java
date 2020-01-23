package net.vivialconnect.model.account;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This is class is a container for a collection of Contact.
 * This is used during API's response JSON deserialization.
 */
public class ContactCollection{

    /**
     * List of Contacts of an account
     */
    @JsonProperty
    private List<Contact> contacts;

    /**
     * @return List of contacts
     */
    public List<Contact> getContacts(){
        return contacts;
    }


    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
    }
}