package net.vivialconnect.model.user;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
public class CredentialWrapperResponse {

    private Credential credential;

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
