package net.vivialconnect.model.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCollection{

    @JsonProperty
    private List<User> users;


    public List<User> getUsers(){
        return users;
    }


    public void setUsers(List<User> users){
        this.users = users;
    }
}