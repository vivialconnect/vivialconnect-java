package net.vivialconnect.model.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.vivialconnect.model.connector.Callback;
import net.vivialconnect.model.connector.PhoneNumber;

public class FormatterRegistry{
	
    private static FormatterRegistry instance;

    private Map<Class<?>, JsonValueFormatter> classToFormatterMap;

    private FormatterRegistry(){
        classToFormatterMap = new HashMap<Class<?>, JsonValueFormatter>();
        classToFormatterMap.put(String.class, new StringFormatter());
        classToFormatterMap.put(Integer.class, new IntegerFormatter());
        classToFormatterMap.put(ArrayList.class, new ListFormatter());
        classToFormatterMap.put(Callback.class, new CallbackFormatter());
        classToFormatterMap.put(PhoneNumber.class, new PhoneNumberFormatter());
        classToFormatterMap.put(HashMap.class, new MapFormatter());
        classToFormatterMap.put(Boolean.class, new BooleanFormatter());
    }


    public JsonValueFormatter getFormatter(Class<?> valueClass){
        JsonValueFormatter formatter = classToFormatterMap.get(valueClass);
        if (formatter == null){
            formatter = new StringFormatter();
        }

        return formatter;
    }


    public static FormatterRegistry getInstance(){
        if (instance == null){
            instance = new FormatterRegistry();
        }

        return instance;
    }
}