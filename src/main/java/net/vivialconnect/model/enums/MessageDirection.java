package net.vivialconnect.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageDirection {

    OUTBOUND_API("outbound-api"), OUTBOUND_REPLY("outbound-reply"), INBOUND("inbound");

    private final String directionValue;

    private MessageDirection(String directionValue) {
        this.directionValue = directionValue;
    }

    @JsonCreator
    public static MessageDirection parseTo(String directionValue) {

        for (MessageDirection direction : MessageDirection.values()) {

            if (direction.directionValue.equals(directionValue)) {
                return direction;
            }

        }

        return null;
    }

    @JsonValue
    public String getDirectionValue() {
        return directionValue;
    }
}