package net.vivialconnect.model.format;

public interface JsonValueFormatter<T>{

    String formatValue(T value);
}