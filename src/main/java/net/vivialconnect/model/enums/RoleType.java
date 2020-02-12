package net.vivialconnect.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleType {
    SYSTEM, CLIENT;

    @JsonCreator
    public static RoleType parseTo(String roleTypeName) {

        for (RoleType roleType : RoleType.values()) {

            if (roleType.name().equalsIgnoreCase(roleTypeName)) {
                return roleType;
            }
        }

        return null;
    }

}
