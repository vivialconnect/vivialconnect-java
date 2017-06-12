package net.vivialconnect.model.format;

public class IntegerFormatter implements JsonValueFormatter{
	
    @Override
    public String formatValue(Object value){
        Integer intValue = (Integer) value;
        return String.valueOf(intValue);
    }
}