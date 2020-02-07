package net.vivialconnect.tests.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.lang.Integer;

import net.vivialconnect.model.connector.*;
import net.vivialconnect.model.enums.MessageDirection;
import net.vivialconnect.model.message.*;
import net.vivialconnect.model.number.*;
import net.vivialconnect.model.number.Number;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.account.Account;
import net.vivialconnect.model.account.Contact;
import net.vivialconnect.model.user.Role;
import net.vivialconnect.model.account.ContactCollection;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.user.User;
import net.vivialconnect.model.user.UserCollection;
import net.vivialconnect.tests.BaseTestCase;
import net.vivialconnect.model.log.LogCollection;

public class MockData implements DataSource {

    private List<AvailableNumber> availableNumbers;
    private List<AssociatedNumber> associatedNumbers = new ArrayList<AssociatedNumber>();
    private List<Contact> contacts;
    private List<User> users;
    private List<Attachment> attachments;
    private List<Message> messages;
    private List<Connector> connectors;
    private Connector connector;
    private LogCollection logs;
    private int numberCountMod = 0;
    private int pendingCount = 0;
    private int connectorCountMod = 0;

    private Random random = new Random();
    private HashMap<Integer, ArrayList<PhoneNumber>> connectorNumbers = new HashMap<Integer, ArrayList<PhoneNumber>>();
    private String[] supportedExtensionsArray = {
            ".m4a",
            ".m4p",
            ".m4b",
            ".m4r",
            ".mp1",
            ".mp2",
            ".mp3",
            ".m1a",
            ".m2a",
            ".mpa",
            ".oga",
            ".flac",
            ".webm",
            ".wav",
            ".amr",
            ".3ga",
            ".3gp",
            ".bmp",
            ".dib",
            ".gif",
            ".jpg",
            ".jpeg",
            ".pjpeg",
            ".png",
            ".ogv",
            ".oga",
            ".ogx",
            ".ogg",
            ".pdf",
            ".rtf",
            ".zip",
            ".tar",
            ".xml",
            ".gz ",
            ".bz2",
            ".gz",
            ".smil",
            ".js",
            ".json",
            ".xml",
            ".avi",
            ".mp4",
            ".m4v",
            ".mpg",
            ".mpeg",
            ".m1v",
            ".mpv",
            ".ogv",
            ".ogx",
            ".ogg",
            ".spx",
            ".ogm",
            ".svg",
            ".tiff",
            ".tif",
            ".webp",
            ".ico",
            ".css",
            ".csv ",
            ".html",
            ".cal",
            ".txt",
            ".js",
            ".vcf",
            ".vcard",
            ".wap",
            ".mov",
            ".qt",
            ".webm",
            ".wmv",
            ".flv"
    };

    private List<String> supportedExtensionsList = Arrays.asList(supportedExtensionsArray);

    @Override
    public Account getAccount() throws VivialConnectException {
        return loadFixture("account", Account.class);
    }

    @Override
    public void updateAccount(Account account) throws VivialConnectException {
        account.setDateModified(new Date());
    }

    @Override
    public AssociatedNumber getNumberById(int numberId) throws VivialConnectException {
        if (numberId < 1) {
            handleInvalidId(numberId);
        }

        return findAssociatedNumber(numberId);
    }

    @Override
    public AssociatedNumber getLocalNumberById(int numberId) throws VivialConnectException {
        if (numberId < 1) {
            handleInvalidId(numberId);
        }

        return findAssociatedNumber(numberId);
    }

    private AssociatedNumber findAssociatedNumber(int numberId) {
        for (AssociatedNumber associatedNumber : loadAssociatedNumbersFromFixture()) {
            if (associatedNumber.getId() == numberId) {
                return associatedNumber;
            }
        }

        return null;
    }

    @Override
    public List<AssociatedNumber> getAssociatedNumbers() throws VivialConnectException {
        return loadAssociatedNumbersFromFixture();
    }

    @Override
    public List<AssociatedNumber> getLocalAssociatedNumbers() throws VivialConnectException {
        List<AssociatedNumber> localAssociatedNumbers = new ArrayList<AssociatedNumber>();
        for (AssociatedNumber associatedNumber : loadAssociatedNumbersFromFixture()) {
            if (associatedNumber.getPhoneNumberType().equals("local")) {
                localAssociatedNumbers.add(associatedNumber);
            }
        }

        return localAssociatedNumbers;
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersInRegion(String region, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByAreaCode(String areaCode, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public List<AvailableNumber> findAvailableNumbersByPostalCode(String postalCode, Map<String, String> filters) throws VivialConnectException {
        return applyFilters(loadAvailableNumbersFromFixture(), filters);
    }

    @Override
    public int numberCount() throws VivialConnectException {
        return loadFixture("number-count", ResourceCount.class, false).getCount() + numberCountMod;
    }

    @Override
    public int numberCountLocal() throws VivialConnectException {
        return loadFixture("number-count-local", ResourceCount.class, false).getCount() + numberCountMod;
    }

    @Override
    public AssociatedNumber buyAvailable(AvailableNumber number) throws VivialConnectException {
        AssociatedNumber boughtNumber = new Number();
        boughtNumber.setPhoneNumber(number.getPhoneNumber());
        boughtNumber.setPhoneNumberType(number.getPhoneNumberType());
        numberCountMod++;
        associatedNumbers.add(boughtNumber);
        return boughtNumber;
    }

    @Override
    public AssociatedNumber buy(String phoneNumber, String areaCode, String phoneNumberType, Map<String, Object> optionalParams) throws VivialConnectException {
        AssociatedNumber number = new Number();
        number.setPhoneNumber(phoneNumber);
        number.setPhoneNumberType(phoneNumberType);
        numberCountMod++;
        associatedNumbers.add(number);
        return number;
    }

    @Override
    public AssociatedNumber buyLocalNumber(String phoneNumber, String areaCode, Map<String, Object> optionalParams) throws VivialConnectException {
        AssociatedNumber number = new Number();
        number.setPhoneNumber(phoneNumber);
        number.setPhoneNumberType("local");
        numberCountMod++;
        associatedNumbers.add(number);
        return number;
    }

    @Override
    public boolean delete(AssociatedNumber localNumber) throws VivialConnectException {
        loadAssociatedNumbersFromFixture().remove(localNumber);
        numberCountMod--;
        return true;
    }

    @Override
    public boolean deleteLocalNumber(AssociatedNumber localNumber) throws VivialConnectException {
        if (!localNumber.getPhoneNumberType().equals("local")) {
            localNumber.setPhoneNumberType("local");
            throw new UnsupportedOperationException("Number must be local");
        }

        loadAssociatedNumbersFromFixture().remove(localNumber);
        numberCountMod--;
        return true;
    }

    @Override
    public void updateNumber(AssociatedNumber number) throws VivialConnectException {
        number.setDateModified(new Date());
    }

    @Override
    public void updateLocalNumber(AssociatedNumber localNumber) throws VivialConnectException {
        if (!localNumber.getPhoneNumberType().equals("local")) {
            localNumber.setPhoneNumberType("local");
            throw new UnsupportedOperationException("Number must be local");
        }
        localNumber.setDateModified(new Date());
    }

    @Override
    public NumberInfo numberLookup(AssociatedNumber number) throws VivialConnectException {
        NumberInfo info = loadFixture("number-info", NumberInfo.class);
        if (info.getPhoneNumber().equals(number.getPhoneNumber().substring(1))) {
            return info;
        }

        return null;
    }

    @Override
    public TaggedNumberCollection getTaggedNumbers(Map<String, String> requestParams) throws VivialConnectException {

        List<Number> numbers = new ArrayList<Number>();
        List<AssociatedNumber> associatedNumbers = this.getAssociatedNumbers();

        if (requestParams != null && requestParams.containsKey("notcontains")) {
            Number mockNumber = new Number();
            numbers.add(mockNumber);
        } else {
            for (AssociatedNumber associatedNumber : associatedNumbers) {
                Number number = (Number) associatedNumber;
                numbers.add(number);
            }
        }

        TaggedNumberCollection numberCollection = new TaggedNumberCollection(associatedNumbers.size(), numbers,
                                                                    0, 1, 0);

        return numberCollection;
    }

    @Override
    public TagCollection updateTags(Map<String, String> tags, AssociatedNumber associatedNumber) throws VivialConnectException {

        if (tags.containsKey("testtag")) {

            if (tags.get("testtag").equals("invalid"))
                throw new VivialConnectException();

        }

        Map<String, String> tagsCopy = new HashMap<String, String>(tags);
        Number number = (Number) associatedNumber;
        number.setTags(tagsCopy);

        TagCollection tagCollection = new TagCollection(tagsCopy);

        return tagCollection;
    }

    @Override
    public TagCollection fetchTags(AssociatedNumber associatedNumber) throws VivialConnectException {

        Number number = (Number) associatedNumber;
        TagCollection tagCollection = new TagCollection(number.getTags());

        return tagCollection;

    }

    @Override
    public TagCollection deleteTags(Map<String, String> tags, AssociatedNumber associatedNumber) throws VivialConnectException {

        Number number = (Number) associatedNumber;

        for (String key : tags.keySet()) {
            number.getTags().remove(key);
        }

        TagCollection tagCollection = new TagCollection(number.getTags());

        return tagCollection;
    }

    @Override
    public List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
        if (filters == null) {
            return loadMessagesFromFixture();
        }

        return applyFilters(loadMessagesFromFixture(), filters);
    }

    @Override
    public Message getMessageById(int messageId) throws VivialConnectException {
        if (messageId < 1) {
            handleInvalidId(messageId);
        }

        for (Message message : loadMessagesFromFixture()) {
            if (message.getId() == messageId) {
                return message;
            }
        }

        return null;
    }

    private void checkForError(Message message) throws VivialConnectException {

        if (message.getBody() == null && message.getFromNumber() != null && message.getToNumber() != null) {
            throw new VivialConnectException(10000, "Message body OR media_urls must be provided", 400, null);
        }

        if (message.getMediaUrls() != null) {

            String[] fileNameParts = message.getMediaUrls().get(0).split("/");
            String filename = fileNameParts[fileNameParts.length - 1];
            String[] fileNameExtensionParts = filename.split("\\.");
            String fileNameExtension = fileNameExtensionParts[fileNameExtensionParts.length - 1];

            if (!supportedExtensionsList.contains("." + fileNameExtension))
                throw new VivialConnectException(10002, null, 400, null);
        }

        if (message.getBody() != null && message.getBody().length() > 2048) {
            throw new VivialConnectException(10003, "Message body must be less than 2048 characters", 400, null);
        }

        if (message.getBody() != null && message.getBody().equals("OPTOUT TEST MESSAGE")) {
            throw new VivialConnectException(10005, "to_number is opted out for messages from from_number", 400, null);
        }

        if (message.getFromNumber() != null && message.getFromNumber().equals("+16162000000")) {
            throw new VivialConnectException(10008, "from_number invalid, inactive, or not owned", 400, null);
        }

        if (message.getConnectorId() == 999999999) {
            throw new VivialConnectException(10009, "connector_id invalid, inactive, or not owned", 400, null);
        }

        if (message.getFromNumber() == null && message.getConnectorId() > 0) {
            throw new VivialConnectException(10012, "Must specify to_number", 400, null);
        }

    }

    @Override
    public void sendMessage(Message message) throws VivialConnectException {

        checkForError(message);

        message.setId(getMessages(null).get(0).getId() + 1);
        message.setDirection(MessageDirection.OUTBOUND_API);
        message.setStatus("accepted");

        Date dateCreated = new Date();
        message.setDateCreated(dateCreated);
        message.setDateModified(dateCreated);

        int numMedia = message.getMediaUrls() != null ? message.getMediaUrls().size() : 0;
        if (numMedia > 0) {
            message.setNumMedia(numMedia);
            message.setMessageType("local_mms");
        }

        messages.add(0, message);
        pendingCount++;
    }

    @Override
    public int messageCount() throws VivialConnectException {
        return loadFixture("message-count", ResourceCount.class, false).getCount() + pendingCount;
    }

    @Override
    public void redactMessage(Message message) throws VivialConnectException {
        message.setBody("");
        message.setDateModified(new Date());
    }

    private List<Attachment> loadAttachmentsFromFixture() {
        if (attachments == null) {
            attachments = loadFixture("attachments", AttachmentCollection.class, false).getAttachments();
        }

        return attachments;
    }

    @Override
    public List<Attachment> getAttachments(Message message) throws VivialConnectException {
        return loadAttachmentsFromFixture();
    }

    @Override
    public Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException {
        Message message = getMessageById(messageId);
        List<Attachment> attachments = getAttachments(message);

        if (attachmentId < 1) {
            handleInvalidId(attachmentId);
        }

        for (Attachment attachment : attachments) {
            if (attachment.getId() == attachmentId) {
                return attachment;
            }
        }

        return null;
    }

    @Override
    public int attachmentCount(int messageId) throws VivialConnectException {
        return loadFixture("attachment-count", ResourceCount.class, false).getCount();
    }

    @Override
    public boolean deleteAttachment(Attachment attachment) throws VivialConnectException {
        return true;
    }

    private List<AssociatedNumber> loadAssociatedNumbersFromFixture() {
        if (associatedNumbers == null) {
            associatedNumbers = loadFixture("associated-numbers", NumberCollection.class, false).getAssociatedNumbers();
        }

        return associatedNumbers;
    }

    private List<AvailableNumber> loadAvailableNumbersFromFixture() {
        if (availableNumbers == null) {
            availableNumbers = loadFixture("available-numbers", NumberCollection.class, false).getAvailableNumbers();
        }

        return availableNumbers;
    }

    private List<Message> loadMessagesFromFixture() {
        if (messages == null) {
            messages = loadFixture("messages", MessageCollection.class, false).getMessages();
        }

        return messages;
    }

    private <T> List<T> applyFilters(List<T> elements, Map<String, String> filters) {
        /* TODO: int page = getPage(filters); */
        int limit = getLimit(filters);
        if (limit > 0) {
            elements = elements.subList(0, limit);
        }

        return elements;
    }

    private int getLimit(Map<String, String> filters) {
        String limit = filters.get("limit");
        if (limit == null) {
            limit = "0";
        }

        return Integer.parseInt(limit);
    }

    protected <T> T loadFixture(String filename, Class<T> type) {
        return loadFixture(filename, type, true);
    }

    protected <T> T loadFixture(String filename, Class<T> type, boolean unwrapRoot) {
        ClassLoader classLoader = BaseTestCase.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(String.format("%s.json", filename));

        try {
            String content = IOUtils.toString(stream);

            ObjectMapper mapper = getObjectMapper();
            if (unwrapRoot) {
                mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            }

            return mapper.reader().forType(type).readValue(content);
        } catch (IOException ioe) {
            System.err.println(String.format("Failed to load fixture ('%s'): %s", filename, ioe));
        }

        return null;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    private void handleInvalidId(int id) throws VivialConnectException {
        String errorMessage = String.format("Invalid id param %d", id);

        VivialConnectException vce = new VivialConnectException(errorMessage, new IOException());
        vce.setResponseCode(400);

        throw vce;
    }

    @Override
    public Contact createContact(Contact contact) throws VivialConnectException {
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact) throws VivialConnectException {
        return contact;
    }

    @Override
    public boolean deleteContact(Contact contact) throws VivialConnectException {
        return true;
    }

    @Override
    public Contact getContactById(int contactId) throws VivialConnectException {
        if (contactId < 1) {
            handleInvalidId(contactId);
        }

        for (Contact contact : loadContactsFromFixture()) {
            if (contact.getId() == contactId) {
                return contact;
            }
        }

        return null;
    }

    @Override
    public List<Contact> getContacts() throws VivialConnectException {
        return loadContactsFromFixture();
    }

    @Override
    public int contactCount() throws VivialConnectException {
        return loadFixture("contact-count", ResourceCount.class, false).getCount();
    }

    private List<Contact> loadContactsFromFixture() {
        if (contacts == null) {
            contacts = loadFixture("contacts", ContactCollection.class, false).getContacts();
        }

        return contacts;
    }



    @Override
    public User createUser(Map<String, Object> attributes) throws VivialConnectException {
        User user = new User();

        if (attributes.containsKey("id")) {
            user.setId(((Integer)attributes.get("id")).intValue());
        }
        if (attributes.containsKey("date_created")) {
            user.setDateCreated((Date)attributes.get("date_created"));
        }
        if (attributes.containsKey("date_modified")) {
            user.setDateModified((Date)attributes.get("date_modified"));
        }
        if (attributes.containsKey("account_id")) {
            user.setAccountId(((Integer)attributes.get("account_id")).intValue());
        }
        if (attributes.containsKey("active")) {
            user.setActive(((Boolean)attributes.get("active")).booleanValue());
        }
        if (attributes.containsKey("verified")) {
            user.setVerified(((Boolean)attributes.get("verified")).booleanValue());
        }
        if (attributes.containsKey("api_key")) {
            user.setApiKey((String)attributes.get("api_key"));
        }
        if (attributes.containsKey("timezone")) {
            user.setTimezone((String)attributes.get("timezone"));
        }
        if (attributes.containsKey("username")) {
            user.setUsername((String)attributes.get("username"));
        }
        if (attributes.containsKey("first_name")) {
            user.setFirstName((String)attributes.get("first_name"));
        }
        if (attributes.containsKey("last_name")) {
            user.setLastName((String)attributes.get("last_name"));
        }
        if (attributes.containsKey("email")) {
            user.setEmail((String)attributes.get("email"));
        }
        if (attributes.containsKey("roles")) {
            user.setRoles((List<Role>)attributes.get("roles"));
        }

        return user;
    }

    @Override
    public boolean deleteUser(User user) throws VivialConnectException {
        return true;
    }

    @Override
    public List<User> getUsers() throws VivialConnectException {
        return loadUsersFromFixture();
    }

    @Override
    public User getUserById(int userId) throws VivialConnectException {
        if (userId < 1) {
            handleInvalidId(userId);
        }

        for (User user : loadUsersFromFixture()) {
            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    @Override
    public int userCount() throws VivialConnectException {
        return loadFixture("user-count", ResourceCount.class, false).getCount();
    }

    private List<User> loadUsersFromFixture() {
        if (users == null) {
            users = loadFixture("users", UserCollection.class, false).getUsers();
        }

        return users;
    }



    @Override
    public LogCollection getLogs(Date startTime, Date endTime) throws VivialConnectException {
        LogCollection logs = loadLogsFromFixture();
        return logs;
    }

    @Override
    public LogCollection getAggregate(Date startTime, Date endTime, String aggregatorType) throws VivialConnectException {
        return loadFixture("logs-aggregate-hours", LogCollection.class, false);
    }

    private LogCollection loadLogsFromFixture() {
        if (logs == null) {
            logs = loadFixture("logs", LogCollection.class, false);
        }

        return logs;
    }




    @Override
    public Connector createConnector() throws VivialConnectException {
        List<Connector> connectors = getConnectors();
        Connector newConnector = new Connector();
        Connector oldConnector = connectors.get(0);
        newConnector.setId(random.nextInt(65536));
        newConnector.setDateCreated(oldConnector.getDateCreated());
        newConnector.setDateModified(oldConnector.getDateModified());
        newConnector.setAccountId(oldConnector.getAccountId());
        newConnector.setActive(oldConnector.isActive());
        newConnector.setName(oldConnector.getName());
        newConnector.setCallbacks(new ArrayList<Callback>());
        newConnector.setPhoneNumbers(new ArrayList<PhoneNumber>());
        connectorCountMod++;
        connectors.add(newConnector);
        return newConnector;
    }

    @Override
    public boolean deleteConnector(Connector conn) throws VivialConnectException {
        if (getConnectors().remove(conn)) {
            connectorCountMod--;
            return true;
        }
        return false;
    }

    @Override
    public Connector updateConnector(Connector conn) throws VivialConnectException {
        return conn;
    }

    @Override
    public List<Connector> getConnectors() throws VivialConnectException {
        return loadConnectorsFromFixture();
    }

    @Override
    public Connector getConnectorById(int connectorId) throws VivialConnectException {
        if (connectorId < 1) {
            handleInvalidId(connectorId);
        }

        for (Connector conn : loadConnectorsFromFixture()) {
            if (conn.getId() == connectorId) {
                return conn;
            }
        }

        return null;
    }

    @Override
    public int connectorCount() throws VivialConnectException {
        return loadFixture("connector-count", ResourceCount.class, false).getCount() + connectorCountMod;
    }

    private List<Connector> loadConnectorsFromFixture() {
        if (connectors == null) {
            connectors = loadFixture("connectors", ConnectorCollection.class, false).getConnectors();
        }

        return connectors;
    }

    @Override
    public ConnectorWithCallbacks createCallbacks(Connector conn) throws VivialConnectException {
        return conn;
    }

    @Override
    public ConnectorWithCallbacks updateCallbacks(Connector conn) throws VivialConnectException {
        return conn;
    }

    @Override
    public ConnectorWithCallbacks deleteAllCallbacks(Connector conn) throws VivialConnectException {
        conn.getCallbacks().clear();
        return conn;
    }

    @Override
    public ConnectorWithCallbacks deleteSingleCallback(Connector conn, Callback callback) throws VivialConnectException {
        conn.getCallbacks().remove(callback);
        return conn;
    }

    @Override
    public ConnectorWithCallbacks deleteCallbacks(Connector conn, List<Callback> callbacks) throws VivialConnectException {
        conn.getCallbacks().removeAll(callbacks);
        return conn;
    }

    @Override
    public ConnectorWithCallbacks getCallbacks(int connectorId) throws VivialConnectException {
        return getConnectorById(connectorId);
    }

    private Connector loadConnectorFromFixture() {
        if (connector == null) {
            connector = loadFixture("connector", Connector.class, false);
        }

        return connector;
    }

    @Override
    public ConnectorWithPhoneNumbers associatePhoneNumbers(Connector conn) throws VivialConnectException {
        Integer id = Integer.valueOf(conn.getId());
        connectorNumbers.put(id, new ArrayList(conn.getPhoneNumbers()));

        return conn;
    }

    @Override
    public ConnectorWithPhoneNumbers updateAssociatedPhoneNumbers(Connector conn) throws VivialConnectException {
        Integer id = Integer.valueOf(conn.getId());
        if (connectorNumbers.containsKey(id)) {
            ArrayList<PhoneNumber> curNumbers = connectorNumbers.get(id);
            for (PhoneNumber pn : conn.getPhoneNumbers()) {
                if (!curNumbers.contains(pn)) {
                    curNumbers.add(pn);
                }
            }
            conn.setPhoneNumbers((ArrayList<PhoneNumber>)curNumbers.clone());
        }
        else {
            connectorNumbers.put(id, new ArrayList(conn.getPhoneNumbers()));
        }

        return conn;
    }

    @Override
    public ConnectorWithPhoneNumbers updateConnectorWithPhoneNumbers(Connector connector) throws VivialConnectException {
         ConnectorWithPhoneNumbers connectorWithPhoneNumbers = getPhoneNumbers(connector.getId());

         connector.setPages(connectorWithPhoneNumbers.getPages());
         connector.setNextPage(connectorWithPhoneNumbers.getNextPage());
         connector.setPreviousPage(connectorWithPhoneNumbers.getPreviousPage());
         connector.setPhoneNumbers(connectorWithPhoneNumbers.getPhoneNumbers());
         connector.setPhoneNumbersCount(connectorWithPhoneNumbers.getPhoneNumbersCount());

         return connector;
    }


    @Override
    public ConnectorWithPhoneNumbers deleteAllPhoneNumbers(Connector conn) throws VivialConnectException {
        Integer id = Integer.valueOf(conn.getId());
        if (connectorNumbers.containsKey(id)) {
            connectorNumbers.get(id).clear();
        }
        conn.getPhoneNumbers().clear();
        return conn;
    }

    @Override
    public ConnectorWithPhoneNumbers deleteSinglePhoneNumber(Connector conn, PhoneNumber phoneNumber) throws VivialConnectException {
        Integer id = Integer.valueOf(conn.getId());
        if (connectorNumbers.containsKey(id)) {
            connectorNumbers.get(id).remove(phoneNumber);
        }
        conn.getPhoneNumbers().remove(phoneNumber);
        return conn;
    }

    @Override
    public ConnectorWithPhoneNumbers deletePhoneNumbers(Connector conn, List<PhoneNumber> phoneNumbers) throws VivialConnectException {
        Integer id = Integer.valueOf(conn.getId());
        if (connectorNumbers.containsKey(id)) {
            connectorNumbers.get(id).removeAll(phoneNumbers);
        }
        conn.getPhoneNumbers().removeAll(phoneNumbers);
        return conn;
    }

    @Override
    public ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId) throws VivialConnectException {
        return getPhoneNumbers(connectorId,1);
    }

    @Override
    public ConnectorWithPhoneNumbers getPhoneNumbers(int connectorId, int page) throws VivialConnectException {

        ConnectorPaginatedPhoneNumbers paginatedConnector;
        Connector connector;

        if(page == 2){
            paginatedConnector = loadFixture("connector-numbers-pg2", ConnectorPaginatedPhoneNumbers.class,false);
            connector = paginatedConnector.getConnector();
            connector.setCurrentPage(2);
        }else{
            paginatedConnector = loadFixture("connector-numbers-pg1", ConnectorPaginatedPhoneNumbers.class,false);
            connector = paginatedConnector.getConnector();
            connector.setCurrentPage(1);
        }

        return connector;
    }

    @Override
    public ConnectorWithPhoneNumbers previousPage(Connector connector) throws VivialConnectException {
        return getPhoneNumbers(connector.getId(),1);
    }

    @Override
    public ConnectorWithPhoneNumbers nextPage(Connector connector) throws VivialConnectException {
        return getPhoneNumbers(connector.getId(),2);
    }

    @Override
    public int phoneNumberCount(int connectorId) throws VivialConnectException {
        Integer id = Integer.valueOf(connectorId);
        if (connectorNumbers.containsKey(id)) {
            return connectorNumbers.get(id).size();
        }

        return 0;
    }

    @Override
    public List<AssociatedNumber> getNumbersForConnectorPagination() throws VivialConnectException {

        Map<String,String> queryParams = new HashMap<String, String>();
        queryParams.put("limit","52");

        List<AvailableNumber> availableNumbers = this.findAvailableNumbersByAreaCode("503", queryParams);

        for(AvailableNumber phoneNumber: availableNumbers){
            this.buyAvailable(phoneNumber);
        }

        return this.getAssociatedNumbers();
    }

    @Override
    public BulkInfo sendBulk(BulkMessage message){
        return loadFixture("bulk-created", BulkInfo.class,false);
    }

    @Override
    public BulkInfo sendBulkWithoutNumbers(BulkMessage bulkMessage) throws VivialConnectException {
        throw new VivialConnectException();
    }

    @Override
    public BulkInfoCollection getCreatedBulks() {
        return loadFixture("bulks", BulkInfoCollection.class,false);
    }

    @Override
    public List<Message> getBulk(String bulkId) throws VivialConnectException {
        return loadFixture("bulk", MessageCollection.class,false).getMessages();
    }

    @Override
    public List<Message> getMessageByDirection(MessageDirection direction) throws VivialConnectException {

        List<Message> messagesByDirection = new ArrayList<Message>();

        for (Message message : getMessages(null)) {

            if (message.getDirection() == direction) {
                messagesByDirection.add(message);
            }

        }

        return messagesByDirection;
    }

}