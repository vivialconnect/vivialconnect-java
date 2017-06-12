package net.vivialconnect.model.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;

/**
 * This class holds Vivial Connect logs a variety of events.
 * <p>
 * Use the logs resources to pull information from our logs relating to your account activity.
 * Access aggregated logs to view information about log activity over time.
 * */
public class Log extends VivialConnectResource {

    private static final long serialVersionUID = -1982193020990089235L;

    /**
     * Unique identifier of the log object.
     */
    @JsonProperty("log_id")
    private String logId;

    /**
     * Unique identifier of the account that owns this log.
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * Account id item id
     */
    @JsonProperty("account_id_item_id")
    private String accountIdItemId;

    /**
     * Account id log type
     */
    @JsonProperty("account_id_log_type")
    private String accountIdLogType;

    /**
     * Account id operator id
     */
    @JsonProperty("account_id_operator_id")
    private String accountIdOperatorId;

    /**
     * The log type as a string. Log-types are typically of the form
     * ITEM_TYPE.ACTION, where ITEM_TYPE is the type of item that was affected
     * and ACTION is what happened to it. For example: message.queued.
     */
    @JsonProperty("log_type")
    private String logType;

    /**
     * The type of item that was affected. Possible values are number, message,
     * account, and user.
     */
    @JsonProperty("item_type")
    private String itemType;

    /**
     * Unique id of item that was affected.
     */
    @JsonProperty("item_id")
    private String itemId;

    /**
     * The type of operator that generated this log. For instance, “user” for a
     * change made by a logged-in user in the Vivia Connect console, “account”
     * for a log created during an API request, or “admin” for an log created by
     * the internal system.
     */
    @JsonProperty("operator_type")
    private String operatorType;

    /**
     * Unique id of operator that caused this log.
     */
    @JsonProperty("operator_id")
    private int operatorId;

    /**
     * The originating system or interface that generated the log. For instance,
     * “web” for a change made by a logged-in user in the Vivial Connect
     * console, “api” for API requests, or “system” for logs caused by an
     * automated or internal system.
     */
    @JsonProperty("origin")
    private String origin;

    /**
     * A free-form json object storing additional data about the log item.
     */
    @JsonProperty("log_data")
    private LogData logData;

    /**
     * Log data Json
     */
    @JsonProperty("log_data_json")
    private String logDataJson;

    /**
     * A UTC timestamp in format YYYYMMDDhhmmssssssss
     */
    @JsonProperty("log_timestamp")
    private String logTimestamp;

    /**
     * A human-readable description of the log.
     */
    @JsonProperty("description")
    private String description;

    static {
        classesWithoutRootValue.add(LogCollection.class);
    }
    
    /**
     * Gets all logs relating to your account activity.
     * <p>
     * StartTime and endTime should be formated in ISO 8601 format like YYYYMMDDThhmmssZ.
     * 
     * @param startTime start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @return a list of log collection
     * @throws VivialConnectException if there is an API-level error
     *
     * @see #getLogs(Date, Date, Map)
     */
    public static LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException {
        return getLogs(startTime, endTime, null);
    }

    /**
     * Search and filter for all logs relating to your account activity.
     * <p>
     * StartTime and endTime should be formated in ISO 8601 format like YYYYMMDDThhmmssZ.
     *
     * @param startTime start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     * <p>
     * <code>log_type</code> – The log type as a string. Log-types are typically of the form ITEM_TYPE.ACTION, 
     * where ITEM_TYPE is the type of item that was affected and ACTION is what happened to it. For example: message.queued.
     * <p>
     * <code>item_id</code> – Unique id of item that was affected.
     * <p>
     * <code>operator_id</code> – Unique id of operator that caused this log.
     * <p>
     * <code>limit</code> – Used for pagination: number of log records to return. Maximum value: 150.
     * <p>
     * <code>start_key</code> – Used for pagination: value of last_key from previous response
     *
     * @return a list of log collection
     * @throws VivialConnectException if there is an API-level error
     *
     * @see #getLogs(Date, Date)
     */
    public static LogCollection getLogs(Date startTime, Date endTime, Map<String, String> queryParameters) throws VivialConnectException {
        queryParameters = buildQueryParams(startTime, endTime, null, queryParameters);
        return request(RequestMethod.GET, classURL(Log.class), null, queryParameters, LogCollection.class);
    }

    /**
     * Returns the list of aggregated logs in your account.
     * <p>
     * Use aggregated logs to view information about log activity over time.
     *
     * @param startTime start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param aggregatorType valid values are: minutes, hours, days, months, years
     * @return a list of log collection
     * @throws VivialConnectException if there is an API-level error
     *
     * @see #getAggregate(Date, Date, String, Map)
     */
    public static LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException {
        return getAggregate(startTime, endTime, aggregatorType, null);
    }

    /**
     * Search and filter the list of aggregated logs in your account.
     * <p>
     * Use aggregated logs to view information about log activity over time.
     * <p>
     *
     * @param startTime start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param aggregatorType valid values are: minutes, hours, days, months, years
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     * <p>
     * <code>log_type</code> – The log type as a string. Log-types are typically of the form ITEM_TYPE.ACTION, where ITEM_TYPE is the 
     * type of item that was affected and ACTION is what happened to it. For example: message.queued.
     * <p>
     * <code>operator_id</code> – Unique id of operator that caused this log.
     * <p>
     * <code>limit</code> – Used for pagination. Number of log records to return. Maximum value: 150.
     * <p>
     * <code>start_key</code> – Used for pagination. Value of last_key from previous response
     * 
     * @return a list of log collection
     * @throws VivialConnectException if there is an API-level error
     *
     * @see #getAggregate(Date, Date, String)
     */
    public static LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType, Map<String, String> queryParameters) throws VivialConnectException {
        queryParameters = buildQueryParams(startTime, endTime, aggregatorType, queryParameters);
        return request(RequestMethod.GET, classURLWithSuffix(Log.class, "aggregate"), null, queryParameters, LogCollection.class);
    }

    private static Map<String, String> buildQueryParams(Date startTime, Date endTime, String aggregatorType, Map<String, String> queryParams) {
        String formattedStartDate = createRequestTimestamp(startTime);
        String formattedEndDate = createRequestTimestamp(endTime);

        if (queryParams == null) {
            queryParams = new HashMap<String, String>();
        }

        queryParams.put("start_time", formattedStartDate);
        queryParams.put("end_time", formattedEndDate);

        if (aggregatorType != null && !aggregatorType.isEmpty()) {
            queryParams.put("aggregator_type", aggregatorType);
        }

        return queryParams;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountIdItemId() {
        return accountIdItemId;
    }

    public void setAccountIdItemId(String accountIdItemId) {
        this.accountIdItemId = accountIdItemId;
    }

    public String getAccountIdLogType() {
        return accountIdLogType;
    }

    public void setAccountIdLogType(String accountIdLogType) {
        this.accountIdLogType = accountIdLogType;
    }

    public String getAccountIdOperatorId() {
        return accountIdOperatorId;
    }

    public void setAccountIdOperatorId(String accountIdOperatorId) {
        this.accountIdOperatorId = accountIdOperatorId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LogData getLogData() {
        return logData;
    }

    public void setLogData(LogData logData) {
        this.logData = logData;
    }

    public String getLogDataJson() {
        return logDataJson;
    }

    public void setLogDataJson(String logDataJson) {
        this.logDataJson = logDataJson;
    }

    public String getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(String logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
