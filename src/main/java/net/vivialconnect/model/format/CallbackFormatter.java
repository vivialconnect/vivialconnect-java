package net.vivialconnect.model.format;

import net.vivialconnect.model.connector.Callback;

public class CallbackFormatter implements JsonValueFormatter{

    @Override
    public String formatValue(Object value){
        Callback callback = (Callback) value;

        JsonBodyBuilder builder = JsonBodyBuilder.emptyJson();
        builder.addParamPair("event_type", callback.getEventType());
        builder.addParamPair("message_type", callback.getMessageType());
        builder.addParamPair("url", callback.getUrl());
        builder.addParamPair("method", callback.getMethod());

        return builder.build();
    }
}