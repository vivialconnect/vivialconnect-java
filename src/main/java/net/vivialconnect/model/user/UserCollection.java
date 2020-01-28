package net.vivialconnect.model.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for a list of users returned by the API
 *
 * @see User
 */
public class UserCollection {

    /**
     * List of users
     */
    @JsonProperty
    private List<User> users;

    /**
     * Returns a list of users
     *
     * @return list of users
     */
    public List<User> getUsers() {
        return users;
    }


    /**
     * Set a list of users
     *
     * @param users list of users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
}