package net.vivialconnect.model.format;

public class BooleanFormatter implements JsonValueFormatter<Boolean> {

    @Override
    public String formatValue(Boolean value) {
        return value.toString();
    }
}
