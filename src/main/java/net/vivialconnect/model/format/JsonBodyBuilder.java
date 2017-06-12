package net.vivialconnect.model.format;

import java.util.Map;
import net.vivialconnect.util.ReflectionUtils;

public class JsonBodyBuilder{

    private String className;
    private StringBuilder builder;

    private FormatterRegistry registry = FormatterRegistry.getInstance();


    private JsonBodyBuilder(){
        this.builder = new StringBuilder();
        this.builder.append("{");
    }


    private JsonBodyBuilder(String className){
        this.className = className;

        this.builder = new StringBuilder();
        this.builder.append("{\"");
        this.builder.append(translateClassName());
        this.builder.append("\":{");
    }


    public static JsonBodyBuilder emptyJson(){
        return new JsonBodyBuilder();
    }


    public static JsonBodyBuilder forClass(Class<?> clazz){
        return new JsonBodyBuilder(ReflectionUtils.className(clazz));
    }


    public static JsonBodyBuilder withCustomClassName(String customClassName){
        return new JsonBodyBuilder(customClassName);
    }


    public JsonBodyBuilder addParamPair(String name, Object value){
        if (value == null){
            return this;
        }

        JsonValueFormatter formatter = registry.getFormatter(value.getClass());

        this.builder.append("\"");
        this.builder.append(name);
        this.builder.append("\":");
        this.builder.append(formatter.formatValue(value));
        this.builder.append(",");

        return this;
    }


    public JsonBodyBuilder addParams(Map<String, Object> params){
        for (String paramName : params.keySet()){
            Object paramValue = params.get(paramName);
            this.addParamPair(paramName, paramValue);
        }

        return this;
    }


    public String build(){
        removeTrailingComma();
        closeJsonObject();

        return this.builder.toString();
    }


    private void closeJsonObject(){
        this.builder.append(hasClassName() ? "}}" : "}");
    }


    private boolean hasClassName(){
        return this.className != null && !this.className.isEmpty();
    }


    private void removeTrailingComma(){
        int lastCharIndex = this.builder.length() - 1;
        char lastChar = this.builder.charAt(lastCharIndex);

        if (lastChar == ','){
            this.builder.deleteCharAt(lastCharIndex);
        }
    }


    private String translateClassName(){
        return separateCamelCase(className, "_").toLowerCase();
    }


    private String separateCamelCase(String name, String separator){
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < name.length(); i++){
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0){
                translation.append(separator);
            }

            translation.append(character);
        }

        return translation.toString();
    }
}