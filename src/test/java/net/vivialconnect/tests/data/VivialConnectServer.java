package net.vivialconnect.tests.data;

import java.util.*;

import net.vivialconnect.client.VivialConnectClient;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.enums.MessageDirection;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.format.EmptyJson;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.message.BulkInfo;
import net.vivialconnect.model.message.BulkInfoCollection;
import net.vivialconnect.model.message.BulkMessage;
import net.vivialconnect.model.number.*;
import net.vivialconnect.model.number.Number;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.format.JsonBodyBuilder;
import net.vivialconnect.model.log.Log;
import net.vivialconnect.model.log.LogCollection;
import net.vivialconnect.model.connector.Connector;
import net.vivialconnect.model.connector.Callback;
import net.vivialconnect.model.connector.PhoneNumber;
import net.vivialconnect.model.connector.ConnectorWithCallbacks;
import net.vivialconnect.model.connector.ConnectorWithPhoneNumbers;

// Test-only subclass of user for creating new users through the API
class AdminUser extends User {
    public static User createUser(Map<String, Object> attributes) throws VivialConnectException {
        JsonBodyBuilder builder = JsonBodyBuilder.withCustomClassName("user");
        if (attributes != null) {
            builder = builder.addParams(attributes);
        }

        User user = new User();
        EmptyJson response = request(RequestMethod.POST, classURLWithSuffix(User.class,"register"), builder.build(), null, EmptyJson.class);

        if(response != null){
            List<User> users = getUsers();
            user = users.get(users.size() - 1);
        }

        return user;
    }
}

public class VivialConnectServer implements DataSource {

    protected VivialConnectServer() {
        initClient();
    }

    private void initClient() {
        String accountId = System.getProperty("vivialconnect.test.account-id");
        String apiKey = System.getProperty("vivialconnect.test.api-key");
        String apiSecret = System.getProperty("vivialconnect.test.api-secret");

        VivialConnectClient.init(Integer.parseInt(accountId), apiKey, apiSecret);

        String baseUrl = System.getProperty("vivialconnect.test.base-url");
        if (baseUrl != null && !baseUrl.isEmpty()) {
            VivialConnectClient.overrideApiBaseUrl(baseUrl);
        }
    }

    @Override
    public Account getAccount() throws VivialConnectException {
        return Account.getAccount();
    }

    @Override
    public void updateAccount(Account account) throws VivialConnectException {
        account.update();
    }

    @Override
    public AssociatedNumber getNumberById(int numberId) throws VivialConnectException {
        return Number.getNumberById(numberId);
    }

    @Override
    public AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException {
        return Number.getLocalNumberById(numberId);
    }

    @Override
    public List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
        return Number.getAssociatedNumbers();
    }

    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
        return Number.getLocalAssociatedNumbers();
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersInRegion(region, filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersByAreaCode(areaCode, filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException {
        return Number.findAvailableNumbersByPostalCode(postalCode, filters);
    }

    @Override
    public int numberCount() throws VivialConnectException {
        return Number.count();
    }

    @Override
    public int numberCountLocal() throws VivialConnectException {
        return Number.countLocal();
    }

    @Override
    public AssociatedNumber buyAvailable(AvailableNumber number) throws VivialConnectException {
        return number.buy();
    }

    @Override
    public AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException {
        return Number.buy(phoneNumber, areaCode, phoneNumberType, optionalParams);
    }

    @Override
    public AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException {
        return Number.buyLocalNumber(phoneNumber, areaCode, optionalParams);
    }

    @Override
    public boolean delete(AssociatedNumber localNumber) throws VivialConnectException {
        return localNumber.delete();
    }

    @Override
    public boolean deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException {
        return localNumber.deleteLocalNumber();
    }

    @Override
    public void updateNumber(AssociatedNumber number) throws VivialConnectException {
        number.update();
    }

    @Override
    public void updateLocalNumber(AssociatedNumber number) throws VivialConnectException {
        number.updateLocalNumber();
    }

    @Override
    public NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException {
        return number.lookup();
    }

    @Override
    public TaggedNumberCollection getTaggedNumbers(Map<String, String> requestParams) throws VivialConnectException {
        return Number.getTaggedNumbers(requestParams);
    }

    @Override
    public TagCollection updateTags(Map<String, String> tags, AssociatedNumber number) throws VivialConnectException {
        return number.updateTags(tags);
    }

    @Override
    public TagCollection fetchTags(AssociatedNumber associatedNumber) throws VivialConnectException {
        return associatedNumber.fetchTags();
    }

    @Override
    public TagCollection deleteTags(Map<String, String> tags, AssociatedNumber associatedNumber) throws VivialConnectException {
        return associatedNumber.deleteTags(tags);
    }

    @Override
    public List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
        return Message.getMessages(filters);
    }

    @Override
    public Message getMessageById(int messageId) throws VivialConnectException {
        return Message.getMessageById(messageId);
    }

    @Override
    public void sendMessage(Message message) throws VivialConnectException {
        message.send();
    }

    @Override
    public void redactMessage(Message message) throws VivialConnectException {
        message.redact();
    }

    @Override
    public int messageCount() throws VivialConnectException {
        return Message.count();
    }

    @Override
    public List<Attachment> getAttachments(Message message) throws VivialConnectException {
        return message.getAttachments();
    }

    @Override
    public Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException{
        return Attachment.getAttachmentById(messageId, attachmentId);
    }

    @Override
    public int attachmentCount(int messageId) throws VivialConnectException{
        return Attachment.count(messageId);
    }

    @Override
    public boolean deleteAttachment(Attachment attachment) throws VivialConnectException{
        return attachment.delete();
    }

    @Override
    public Contact createContact(Contact contact) throws VivialConnectException {
        return contact.create();
    }

    @Override
    public Contact updateContact(Contact contact) throws VivialConnectException {
        return contact.update();
    }

    @Override
    public boolean deleteContact(Contact contact) throws VivialConnectException {
        return contact.delete();
    }

    @Override
    public List<Contact> getContacts() throws VivialConnectException {
        return Contact.getContacts();
    }

    @Override
    public Contact getContactById(int contactId) throws VivialConnectException {
        return Contact.getContactById(contactId);
    }

    @Override
    public int contactCount() throws VivialConnectException {
        return Contact.count();
    }

    @Override
    public User createUser(Map<String, Object> attributes) throws VivialConnectException {
        return AdminUser.createUser(attributes);
    }

    @Override
    public boolean deleteUser(User user) throws VivialConnectException {
        return user.delete();
    }

    @Override
    public List<User> getUsers() throws VivialConnectException {
        return User.getUsers();
    }

    @Override
    public User getUserById(int userId) throws VivialConnectException {
        return User.getUserById(userId);
    }

    @Override
    public int userCount() throws VivialConnectException {
        return User.count();
    }

    @Override
    public LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException {
        return Log.getLogs(startTime, endTime);
    }

    @Override
    public LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException {
        return Log.getAggregate(startTime, endTime, aggregatorType);
    }

    @Override
    public Connector createConnector() throws VivialConnectException {
        Connector connector = new Connector();
        connector.create();
        return connector;
    }

    @Override
    public Connector getConnectorById(int connectorId) throws VivialConnectException {
        return Connector.getConnectorById(connectorId);
    }

    @Override
    public List<Connector> getConnectors() throws VivialConnectException {
        return Connector.getConnectors();
    }

    @Override
    public int connectorCount()  throws VivialConnectException {
        return Connector.count();
    }

    @Override
    public Connector updateConnector(Connector connector) throws VivialConnectException {
        return connector.update();
    }

    @Override
    public boolean deleteConnector(Connector connector) throws VivialConnectException {
        return connector.delete();
    }

    @Override
    public ConnectorWithCallbacks createCallbacks(Connector connector) throws VivialConnectException {
        return connector.createCallbacks();
    }

    @Override
    public ConnectorWithCallbacks updateCallbacks(Connector connector) throws VivialConnectException {
        return connector.updateCallbacks();
    }

    @Override
    public ConnectorWithCallbacks deleteAllCallbacks(Connector connector) throws VivialConnectException {
        return connector.deleteAllCallbacks();
    }

    @Override
    public ConnectorWithCallbacks deleteSingleCallback(Connector connector, Callback callback) throws VivialConnectException {
        return connector.deleteSingleCallback(callback);
    }

    @Override
    public ConnectorWithCallbacks deleteCallbacks(Connector connector, List<Callback> callbacks) throws VivialConnectException {
        return connector.deleteCallbacks(callbacks);
    }

    @Override
    public ConnectorWithCallbacks getCallbacks(int connectorId) throws VivialConnectException {
        return Callback.getCallbacks(connectorId);
    }

    @Override
    public ConnectorWithPhoneNumbers associatePhoneNumbers(Connector connector) throws VivialConnectException {
        return connector.associatePhoneNumbers();
    }

    @Override
    public ConnectorWithPhoneNumbers updateAssociatedPhoneNumbers(Connector connector) throws VivialConnectException {
        return connector.updateAssociatedPhoneNumbers();
    }

    @Override
    public ConnectorWithPhoneNumbers updateConnectorWithPhoneNumbers(Connector connector) throws VivialConnectException {
        return updateAssociatedPhoneNumbers(connector);
    }

    @Override
    public ConnectorWithPhoneNumbers deleteAllPhoneNumbers(Connector connector) throws VivialConnectException {
        return connector.deleteAllPhoneNumbers();
    }

    @Override
    public ConnectorWithPhoneNumbers deleteSinglePhoneNumber(Connector connector, PhoneNumber phoneNumber) throws VivialConnectException {
        return connector.deleteSinglePhoneNumber(phoneNumber);
    }

    @Override
    public ConnectorWithPhoneNumbers deletePhoneNumbers(Connector connector, List<PhoneNumber> phoneNumbers) throws VivialConnectException {
        return connector.deletePhoneNumbers(phoneNumbers);
    }

    @Override
    public ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId) throws VivialConnectException {
        return PhoneNumber.getPhoneNumbers(connectorId);
    }

    @Override
    public ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId, int page) throws VivialConnectException {
        return PhoneNumber.getPhoneNumbers(connectorId,page);
    }

    @Override
    public ConnectorWithPhoneNumbers previousPage(Connector connector) throws VivialConnectException {
        connector.previousPage();
        return connector;
    }

    @Override
    public ConnectorWithPhoneNumbers nextPage(Connector connector) throws VivialConnectException {
        connector.nextPage();
        return connector;
    }

    @Override
    public int phoneNumberCount(int connectorId) throws VivialConnectException {
        return PhoneNumber.count(connectorId);
    }

    @Override
    public List<AssociatedNumber> getNumbersForConnectorPagination() throws VivialConnectException {
        return  this.getLocalAssociatedNumbers();
    }

    @Override
    public BulkInfo sendBulk(BulkMessage bulkMessage) throws VivialConnectException {
        return bulkMessage.send();
    }

    @Override
    public BulkInfo sendBulkWithoutNumbers(BulkMessage bulkMessage) throws VivialConnectException {
        return bulkMessage.send();
    }

    @Override
    public BulkInfoCollection getCreatedBulks() throws VivialConnectException {
        return BulkMessage.getBulksCreated();
    }

    @Override
    public List<Message> getBulk(String bulkId) throws VivialConnectException{
        return BulkMessage.getBulk(bulkId);
	}

    @Override
    public List<Message> getMessageByDirection(MessageDirection direction) throws VivialConnectException {
        List<Message> messagesByDirection = new ArrayList<Message>();

        for(Message message: getMessages(null)){

            if(message.getDirection() == direction){
                messagesByDirection.add(message);
            }

        }

        return messagesByDirection;
    }
}