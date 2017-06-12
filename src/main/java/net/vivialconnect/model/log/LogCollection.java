package net.vivialconnect.model.log;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogCollection{

    @JsonProperty("last_key")
    private String lastKey;


    @JsonProperty("log_items")
    private List<Log> logs;


    public String getLastKey(){
        return lastKey;
    }


    public void setLastKey(String lastKey){
        this.lastKey = lastKey;
    }


    public List<Log> getLogs(){
        return logs;
    }


    public void setLogs(List<Log> logs){
        this.logs = logs;
    }
}