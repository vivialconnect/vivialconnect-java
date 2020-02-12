package net.vivialconnect.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CallbackMethod {
    GET,
    POST,
    PUT;

    @JsonCreator
    public static CallbackMethod toParse(String value){
        return CallbackMethod.valueOf(value.toUpperCase());
    }

}
