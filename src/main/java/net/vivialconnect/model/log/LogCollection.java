package net.vivialconnect.model.log;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for map a list of logs returned by the API
 */
public class LogCollection {

    /**
     * Used for pagination. Can be sent with next request as start_key to get next set of results.
     */
    @JsonProperty("last_key")
    private String lastKey;

    /**
     * List of logs
     */
    @JsonProperty("log_items")
    private List<Log> logs;

    /**
     * Used for pagination. Can be sent with next request as start_key to get next set of results.
     *
     * @return last key value
     */
    public String getLastKey() {
        return lastKey;
    }

    /**
     * Set last key value
     *
     * @param lastKey last key value
     */
    public void setLastKey(String lastKey) {
        this.lastKey = lastKey;
    }

    /**
     * List of logs
     *
     * @return list of logs
     */
    public List<Log> getLogs() {
        return logs;
    }

    /**
     * Set a list of logs
     *
     * @param logs list of logs
     */
    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}