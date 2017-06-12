package net.vivialconnect.model.format;

import java.util.List;

public class ListFormatter implements JsonValueFormatter{
	
    private StringBuilder builder;


    @Override
    public String formatValue(Object value){
        this.builder = new StringBuilder();
        this.builder.append("[");

        List list = (List) value;

        for (Object object : list){
            JsonValueFormatter formatter = FormatterRegistry.getInstance().getFormatter(object.getClass());
            builder.append(formatter.formatValue(object)).append(",");
        }

        return builder.deleteCharAt(builder.length() - 1).append("]").toString();
    }
}