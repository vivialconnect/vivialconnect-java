package net.vivialconnect.model.user;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Wrapper class to receive a collection of credentials from the API.
 */
@JsonRootName("user")
public class CredentialCollection {

    private List<Credential> credentials;

    /**
     * Returns list of credentials
     * @return list of credentials
     */
    public List<Credential> getCredentials() {
        return credentials;
    }

    /**
     * Set a collection of credentials
     * @param credentials list of credentials
     */
    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }
}
