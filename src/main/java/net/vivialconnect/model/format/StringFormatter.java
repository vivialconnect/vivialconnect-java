package net.vivialconnect.model.format;

public class StringFormatter implements JsonValueFormatter{
	
    @Override
    public String formatValue(Object value){
        StringBuilder builder = new StringBuilder();
        builder.append("\"");
        builder.append(value);
        builder.append("\"");

        return builder.toString();
    }
}