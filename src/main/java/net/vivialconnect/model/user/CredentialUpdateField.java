package net.vivialconnect.model.user;

/***
 * The API allows to modify some fields of a credential, this enum contains the fields that can be used during a
 * credential update.
 */
public enum CredentialUpdateField {
    ACTIVE("active"), NAME("name");

    private String fieldName;

    private CredentialUpdateField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
