package net.vivialconnect.tests.data;

import java.util.List;
import java.util.Map;
import java.util.Date;

import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.message.BulkInfo;
import net.vivialconnect.model.message.BulkInfoCollection;
import net.vivialconnect.model.number.AssociatedNumber;
import net.vivialconnect.model.number.AvailableNumber;
import net.vivialconnect.model.number.NumberInfo;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;
import net.vivialconnect.model.connector.Connector;
import net.vivialconnect.model.connector.Callback;
import net.vivialconnect.model.connector.PhoneNumber;
import net.vivialconnect.model.connector.ConnectorWithCallbacks;
import net.vivialconnect.model.connector.ConnectorWithPhoneNumbers;

public interface DataSource {

    // Account

    Account getAccount() throws VivialConnectException;

    void updateAccount(Account account) throws VivialConnectException;

    // Number

    AssociatedNumber getNumberById(int numberId) throws VivialConnectException;

    AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException;

    List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException;

    List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException;

    List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException;

    int numberCount() throws VivialConnectException;

    int numberCountLocal() throws VivialConnectException;

    AssociatedNumber buyAvailable(AvailableNumber number) throws VivialConnectException;

    AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException;

    AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException;

    boolean delete(AssociatedNumber localNumber) throws VivialConnectException;

    boolean deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException;

    void updateNumber(AssociatedNumber number) throws VivialConnectException;

    void updateLocalNumber(AssociatedNumber number) throws VivialConnectException;

    NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException;

    // Message

    List<Message> getMessages(Map<String, String> filters) throws VivialConnectException;

    int messageCount() throws VivialConnectException;

    Message getMessageById(int messageId) throws VivialConnectException;

    void sendMessage(Message message) throws VivialConnectException;

    void redactMessage(Message message) throws VivialConnectException;

    List<Attachment> getAttachments(Message message) throws VivialConnectException;

    // Attachment

    Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException;

    int attachmentCount(int messageId) throws VivialConnectException;

    boolean deleteAttachment(Attachment attachment) throws VivialConnectException;

    //Contact

    Contact createContact(Contact contact) throws VivialConnectException;

    Contact updateContact(Contact contact) throws VivialConnectException;

    boolean deleteContact(Contact contact) throws VivialConnectException;

    List<Contact> getContacts() throws VivialConnectException;

    Contact getContactById(int contactId) throws VivialConnectException;

    int contactCount() throws VivialConnectException;

    //User

    User createUser(Map<String, Object> attributes) throws VivialConnectException;

    boolean deleteUser(User user) throws VivialConnectException;

    boolean updateUserPassword(User user, String oldPassword, String newPassword) throws VivialConnectException;

    List<User> getUsers() throws VivialConnectException;

    User getUserById(int userId) throws VivialConnectException;

    int userCount() throws VivialConnectException;

    // Logs

    LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException;

    LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException;

    // Connectors

    Connector createConnector() throws VivialConnectException;

    Connector getConnectorById(int connectorId) throws VivialConnectException;

    List<Connector> getConnectors() throws VivialConnectException;

    int connectorCount()  throws VivialConnectException;

    Connector updateConnector(Connector connector) throws VivialConnectException;

    boolean deleteConnector(Connector connector) throws VivialConnectException;

    ConnectorWithCallbacks createCallbacks(Connector connector) throws VivialConnectException;

    ConnectorWithCallbacks updateCallbacks(Connector connector) throws VivialConnectException;

    ConnectorWithCallbacks deleteAllCallbacks(Connector connector) throws VivialConnectException;

    ConnectorWithCallbacks deleteSingleCallback(Connector connector, Callback callback) throws VivialConnectException;

    ConnectorWithCallbacks deleteCallbacks(Connector connector, List<Callback> callbacks) throws VivialConnectException;

    ConnectorWithCallbacks getCallbacks(int connectorId) throws VivialConnectException;

    ConnectorWithPhoneNumbers associatePhoneNumbers(Connector connector) throws VivialConnectException;

    ConnectorWithPhoneNumbers updateAssociatedPhoneNumbers(Connector connector) throws VivialConnectException;

    ConnectorWithPhoneNumbers deleteAllPhoneNumbers(Connector connector) throws VivialConnectException;

    ConnectorWithPhoneNumbers deleteSinglePhoneNumber(Connector connector, PhoneNumber phoneNumber) throws VivialConnectException;

    ConnectorWithPhoneNumbers deletePhoneNumbers(Connector connector, List<PhoneNumber> phoneNumbers) throws VivialConnectException;

    ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId) throws VivialConnectException;

    int phoneNumberCount(int connectorId) throws VivialConnectException;


    //Bulk

    BulkInfo sendBulk(Message message) throws VivialConnectException;

    BulkInfoCollection getCreatedBulks() throws VivialConnectException;
    
    List<Message> getBulk(String bulkId) throws VivialConnectException;
    
}