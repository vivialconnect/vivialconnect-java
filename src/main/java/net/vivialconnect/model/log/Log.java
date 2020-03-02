package net.vivialconnect.model.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.VivialConnectException;

import net.vivialconnect.model.error.BadRequestException;
import net.vivialconnect.model.error.ServerErrorException;
import net.vivialconnect.model.error.ApiRequestException;
import net.vivialconnect.model.error.ForbiddenAccessException;
import net.vivialconnect.model.error.UnauthorizedAccessException;

/**
 * This class holds Vivial Connect logs a variety of events.
 * <p>
 * Use the logs resources to pull information from our logs relating to your account activity.
 * Access aggregated logs to view information about log activity over time.
 * <p>
 * For more info, visit <a href="https://dashboard.vivialconnect.net/docs/api/logs.html#">Logs</a>
 */
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
     * @param endTime   end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @return a list of log collection
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getLogs(Date, Date, Map)
     */
    public static LogCollection getLogs(Date startTime, Date endTime) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getLogs(startTime, endTime, null);
    }

    /**
     * Search and filter for all logs relating to your account activity.
     * <p>
     * StartTime and endTime should be formated in ISO 8601 format like YYYYMMDDThhmmssZ.
     *
     * @param startTime       start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime         end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     *                        <p>
     *                        <code>log_type</code> – The log type as a string. Log-types are typically of the form ITEM_TYPE.ACTION,
     *                        where ITEM_TYPE is the type of item that was affected and ACTION is what happened to it. For example: message.queued.
     *                        <p>
     *                        <code>item_id</code> – Unique id of item that was affected.
     *                        <p>
     *                        <code>operator_id</code> – Unique id of operator that caused this log.
     *                        <p>
     *                        <code>limit</code> – Used for pagination: number of log records to return. Maximum value: 150.
     *                        <p>
     *                        <code>start_key</code> – Used for pagination: value of last_key from previous response
     * @return a list of log collection
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getLogs(Date, Date)
     */
    public static LogCollection getLogs(Date startTime, Date endTime, Map<String, String> queryParameters) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        queryParameters = buildQueryParams(startTime, endTime, null, queryParameters);
        return request(RequestMethod.GET, classURL(Log.class), null, queryParameters, LogCollection.class);
    }

    /**
     * Returns the list of aggregated logs in your account.
     * <p>
     * Use aggregated logs to view information about log activity over time.
     *
     * @param startTime      start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime        end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param aggregatorType valid values are: minutes, hours, days, months, years
     * @return a list of log collection
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getAggregate(Date, Date, String, Map)
     */
    public static LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
        return getAggregate(startTime, endTime, aggregatorType, null);
    }

    /**
     * Search and filter the list of aggregated logs in your account.
     * <p>
     * Use aggregated logs to view information about log activity over time.
     * <p>
     *
     * @param startTime       start date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param endTime         end date and time in ISO 8601 format like YYYYMMDDThhmmssZ
     * @param aggregatorType  valid values are: minutes, hours, days, months, years
     * @param queryParameters a map of {@link String } key-value pairs used to filter results, possible values are:
     *                        <p>
     *                        <code>log_type</code> – The log type as a string. Log-types are typically of the form ITEM_TYPE.ACTION, where ITEM_TYPE is the
     *                        type of item that was affected and ACTION is what happened to it. For example: message.queued.
     *                        <p>
     *                        <code>operator_id</code> – Unique id of operator that caused this log.
     *                        <p>
     *                        <code>limit</code> – Used for pagination. Number of log records to return. Maximum value: 150.
     *                        <p>
     *                        <code>start_key</code> – Used for pagination. Value of last_key from previous response
     * @return a list of log collection
     * @throws ForbiddenAccessException if the user does not have permission to the API resource
     * @throws BadRequestException if the request params and/or payload  are not valid
     * @throws UnauthorizedAccessException if any of the auth properties account ID, API Key and/or API secret are not valid
     * @throws ServerErrorException if the server is unable to process the request
     * @throws ApiRequestException if an API error occurs
     * @see #getAggregate(Date, Date, String)
     */
    public static LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType, Map<String, String> queryParameters) throws ForbiddenAccessException, BadRequestException, UnauthorizedAccessException, ServerErrorException, ApiRequestException {
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

    /**
     * Unique identifier of the logs object
     *
     * @return log ID value
     */
    public String getLogId() {
        return logId;
    }

    /**
     * Set unique identifier of the logs object.
     *
     * @param logId log ID value
     */
    public void setLogId(String logId) {
        this.logId = logId;
    }

    /**
     * Unique identifier of the account that this log is part of
     *
     * @return Account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set user's account ID
     *
     * @param accountId account ID value
     */
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

    /**
     * Log type
     *
     * @return log type value
     */
    public String getLogType() {
        return logType;
    }

    /**
     * Set log type
     *
     * @param logType log type value
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * The type of item that was affected. Possible values are number, message, account, and user.
     *
     * @return item type value
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Set the item type for this log
     *
     * @param itemType item type value
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * Unique id of item that was affected.
     *
     * @return item ID
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * Set item ID
     *
     * @param itemId item ID value
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * The type of operator that generated this log
     *
     * @return operator type value
     */
    public String getOperatorType() {
        return operatorType;
    }

    /**
     * Set operator type
     *
     * @param operatorType operator type value
     */
    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    /**
     * Unique id of operator that caused this log
     *
     * @return operator ID value
     */
    public int getOperatorId() {
        return operatorId;
    }

    /**
     * Set the operator ID for this log
     *
     * @param operatorId operator ID value
     */
    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * The originating system or interface that generated the log
     *
     * @return origin value
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Set origin that generate the log
     *
     * @param origin origin value
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Parsed log data
     *
     * @return parsed log data object
     * @see Log#getLogDataJson()
     */
    public LogData getLogData() {
        return logData;
    }

    /**
     * Set log data
     *
     * @param logData log data object
     */
    public void setLogData(LogData logData) {
        this.logData = logData;
    }

    /**
     * A free-form json object storing additional data about the log item
     *
     * @return JSON String representation of the log
     */
    public String getLogDataJson() {
        return logDataJson;
    }

    /**
     * Set log data as JSON
     *
     * @param logDataJson log data as JSON
     */
    public void setLogDataJson(String logDataJson) {
        this.logDataJson = logDataJson;
    }

    /**
     * UTC timestamp in format YYYYMMDDhhmmssssssss
     *
     * @return timestamp value
     */
    public String getLogTimestamp() {
        return logTimestamp;
    }

    /**
     * Set log timestamp value
     *
     * @param logTimestamp timestamp value
     */
    public void setLogTimestamp(String logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    /**
     * A human-readable description of the log.
     *
     * @return log's description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set log description
     *
     * @param description log description value
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
