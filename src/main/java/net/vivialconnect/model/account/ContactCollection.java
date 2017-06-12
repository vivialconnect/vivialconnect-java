package net.vivialconnect.model.account;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactCollection{
	
    @JsonProperty
    private List<Contact> contacts;


    public List<Contact> getContacts(){
        return contacts;
    }


    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
    }
}