package net.vivialconnect.model.format;

import java.util.Map;

public class MapFormatter implements JsonValueFormatter<Map<String,Object>> {

    @Override
    public String formatValue(Map<String, Object> value) {
        JsonBodyBuilder jsonObject = JsonBodyBuilder.emptyJson();

        for(String key: value.keySet()){
            jsonObject.addParamPair(key, value.get(key));
        }

        return jsonObject.build();
    }
}
