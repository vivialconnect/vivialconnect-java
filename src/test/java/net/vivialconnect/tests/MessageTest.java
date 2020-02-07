package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assume.assumeTrue;

import org.hamcrest.Matchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.vivialconnect.model.enums.MessageDirection;
import net.vivialconnect.model.error.*;
import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.message.BulkInfo;
import net.vivialconnect.model.message.BulkInfoCollection;
import net.vivialconnect.model.message.BulkMessage;

import net.vivialconnect.tests.data.DataSource;
import org.junit.Rule;
import org.junit.Test;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;
import org.junit.rules.ExpectedException;

public class MessageTest extends BaseTestCase {

    private static final String MESSAGE_BODY = "Message from Vivial Connect Test Suite";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_send_message_with_attachments() throws VivialConnectException, InterruptedException {
        List<AssociatedNumber> numbers = getDataSource().getAssociatedNumbers();
        AssociatedNumber fromNumberAssociated = numbers.get(0);
        AssociatedNumber toNumberAssociated = numbers.get(1);

        String fromNumber = fromNumberAssociated.getPhoneNumber();
        String toNumber = toNumberAssociated.getPhoneNumber();

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(toNumber);
        message.setBody(MESSAGE_BODY);
        message.addMediaUrl("https://code.org/images/apple-touch-icon-precomposed.png");

        assertEquals(0, message.getId());

        getDataSource().sendMessage(message);

        assertTrue(message.getId() > 0);
        assertNotNull(message.getDateCreated());
        assertEquals(MESSAGE_BODY, message.getBody());
        assertEquals(fromNumber, message.getFromNumber());
        assertEquals(toNumber, message.getToNumber());
        assertEquals("local_mms", message.getMessageType());
        assertNotEquals("delivered", message.getStatus());

        // Have to sleep, the attachments don't show up immediately for some reason
        TimeUnit.SECONDS.sleep(20);

        // Check attachment count
        List<Attachment> attachments = getDataSource().getAttachments(message);
        assertEquals(attachments.size(), getDataSource().attachmentCount(message.getId()));

        Attachment attachment = attachments.get(0);
        assertEquals(attachment.getId(), getDataSource().getAttachmentById(message.getId(), attachment.getId()).getId());

        assertTrue(getDataSource().deleteAttachment(attachment));
    }

    @Test
    public void test_message_count() throws VivialConnectException {
        assertTrue(messageCount() > 0);
    }

    @Test
    public void test_get_message() throws VivialConnectException {
        Message message = getMessageById();

        assertNotNull(message);
        assertTrue(message.getId() > 0);
        assertNotNull(message.getDateCreated());
        assertNotNull(message.getDateModified());
        assertNotNull(message.getStatus());
        assertNotNull(message.getBody());
    }

    @Test
    public void test_get_messages() throws VivialConnectException {
        assertEquals(getMessages().size(), messageCount());
    }

    @Test
    public void test_get_messages_with_limit() throws VivialConnectException {
        assertEquals(getMessages(withLimitOf(1)).size(), 1);
    }

    @Test(expected = VivialConnectException.class)
    public void test_get_message_with_invalid_id_throws_vivial_connect_exception() throws VivialConnectException {
        getMessageById(0);
    }

    @Test
    public void test_redact_message() throws VivialConnectException {
        Message message = getMessageById();

        assertNotNull(message.getBody());

        redactMessage(message);

        assertEquals("", message.getBody());
        assertNotNull(message.getDateModified().getTime());
    }

    @Test
    public void test_create_bulk_message() throws VivialConnectException {

        List<AssociatedNumber> numbers = getDataSource().getAssociatedNumbers();
        String fromNumber = numbers.get(0).getPhoneNumber();
        List<String> bulkSendNumberList = new ArrayList<String>();

        for (AssociatedNumber number : numbers.subList(1, numbers.size())) {
            bulkSendNumberList.add(number.getPhoneNumber());
        }

        BulkMessage bulkMessage = new BulkMessage();

        bulkMessage.setFromNumber(fromNumber);
        bulkMessage.setBody("Testing Bulk send...");
        bulkMessage.setToNumbers(bulkSendNumberList);

        BulkInfo bulkInfo = getDataSource().sendBulk(bulkMessage);

        assertNotNull(bulkInfo);
        assertNotNull(bulkInfo.getBulkId());

    }

    @Test
    public void test_get_bulks_sent() throws VivialConnectException {

        BulkInfoCollection bulksSent = getDataSource().getCreatedBulks();
        List<BulkInfo> bulks = bulksSent.getBulkList();
        String someBulkId = bulks.get(0).getBulkId();

        List<Message> bulkMessages = getBulkMessages(someBulkId);

        assertNotNull(bulkMessages);
        assertTrue("Bulks count is zero", bulksSent.getCount() > 0);
        assertTrue("Bulk pages is zero", bulksSent.getPages() > 0);
        assertFalse("Bulk message list is empty", bulkMessages.isEmpty());

        for (Message message : bulkMessages) {
            assertNotNull(String.format("Message %d has a null bulk ID", message.getId()), message.getBulkId());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void test_send_bulk_empty_to_numbers() throws VivialConnectException {

        BulkMessage bulkMessage = new BulkMessage();
        bulkMessage.setBody("Testing Bulk send...");

        getDataSource().sendBulkWithoutNumbers(bulkMessage);
    }

    @Test
    public void test_error_code_10000() throws VivialConnectException {
        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String fromNumber = associatedNumbers.get(0).getPhoneNumber();
        String toNumber = associatedNumbers.get(1).getPhoneNumber();

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(toNumber);

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10000)));
        expectedException.expectMessage("Message body OR media_urls must be provided");

        dataSource.sendMessage(message);

    }

    @Test
    public void test_error_code_10002() throws VivialConnectException {
        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String fromNumber = associatedNumbers.get(0).getPhoneNumber();
        String toNumber = associatedNumbers.get(1).getPhoneNumber();

        List<String> mediaUrls = new ArrayList<String>();
        mediaUrls.add("https://file-examples.com/wp-content/uploads/2017/08/file_example_PPT_500kB.ppt");

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(toNumber);
        message.setMediaUrls(mediaUrls);
        message.setBody("Hello");

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10002)));

        dataSource.sendMessage(message);

    }

    @Test
    public void test_error_code_10003() throws VivialConnectException {
        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String fromNumber = associatedNumbers.get(0).getPhoneNumber();
        String toNumber = associatedNumbers.get(1).getPhoneNumber();

        String bodyContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque consectetur eu ipsum at accumsan. " +
                "Nullam sodales et orci et tincidunt. Quisque rhoncus cursus arcu, eget porta mauris. Phasellus sagittis non odio vel" +
                " faucibus. Cras ullamcorper eu lectus et viverra. Etiam nec arcu ligula. Aliquam porttitor mi a leo blandit, vitae " +
                "faucibus quam ultrices. Sed ac odio diam. Vestibulum vel euismod magna, vel fringilla nunc. Pellentesque finibus libero" +
                " massa, sit amet fringilla magna facilisis vitae. Vestibulum non nunc ac massa tincidunt blandit. Maecenas vel " +
                "justo accumsan, imperdiet lectus non, mollis sapien. Cras vestibulum nunc pellentesque pellentesque efficitur. " +
                "Sed in odio ullamcorper ante sodales fringilla sed ac lorem." +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec mi erat, congue non nibh " +
                "vel, fermentum ullamcorper justo. Aenean eget scelerisque dolor. Curabitur et imperdiet lacus. Mauris non efficitur metus," +
                " in ullamcorper augue. Donec vitae dui feugiat, porta augue in, porta arcu. Pellentesque dictum sapien quam, sed vulputate " +
                "purus fermentum sed." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque consectetur eu ipsum at accumsan. " +
                "Nullam sodales et orci et tincidunt. Quisque rhoncus cursus arcu, eget porta mauris. Phasellus sagittis non odio vel" +
                " faucibus. Cras ullamcorper eu lectus et viverra. Etiam nec arcu ligula. Aliquam porttitor mi a leo blandit, vitae " +
                "faucibus quam ultrices. Sed ac odio diam. Vestibulum vel euismod magna, vel fringilla nunc. Pellentesque finibus libero" +
                " massa, sit amet fringilla magna facilisis vitae. Vestibulum non nunc ac massa tincidunt blandit. Maecenas vel " +
                "justo accumsan, imperdiet lectus non, mollis sapien. Cras vestibulum nunc pellentesque pellentesque efficitur. " +
                "Sed in odio ullamcorper ante sodales fringilla sed ac lorem." +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec mi erat, congue non nibh " +
                "vel, fermentum ullamcorper justo. Aenean eget scelerisque dolor. Curabitur et imperdiet lacus. Mauris non efficitur metus," +
                " in ullamcorper augue. Donec vitae dui feugiat, porta augue in, porta arcu. Pellentesque dictum sapien quam, sed vulputate " +
                "purus fermentum sed." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque consectetur eu ipsum at accumsan. " +
                "Nullam sodales et orci et tincidunt. Quisque rhoncus cursus arcu, eget porta mauris. Phasellus sagittis non odio vel" +
                " faucibus. Cras ullamcorper eu lectus et viverra. Etiam nec arcu ligula. Aliquam porttitor mi a leo blandit, vitae " +
                "faucibus quam ultrices. Sed ac odio diam. Vestibulum vel euismod magna, vel fringilla nunc. Pellentesque finibus libero" +
                " massa, sit amet fringilla magna facilisis vitae. Vestibulum non nunc ac massa tincidunt blandit. Maecenas vel " +
                "justo accumsan, imperdiet lectus non, mollis sapien. Cras vestibulum nunc pellentesque pellentesque efficitur. " +
                "Sed in odio ullamcorper ante sodales fringilla sed ac lorem." +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec mi erat, congue non nibh " +
                "vel, fermentum ullamcorper justo. Aenean eget scelerisque dolor. Curabitur et imperdiet lacus. Mauris non efficitur metus," +
                " in ullamcorper augue. Donec vitae dui feugiat, porta augue in, porta arcu. Pellentesque dictum sapien quam, sed vulputate " +
                "purus fermentum sed.";

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(toNumber);
        message.setBody(bodyContent);

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10003)));
        expectedException.expectMessage("Message body must be less than 2048 characters");

        dataSource.sendMessage(message);

    }


    @Test
    public void test_error_code_10005() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String fromNumber = associatedNumbers.get(0).getPhoneNumber();
        String toNumber = associatedNumbers.get(1).getPhoneNumber();

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setToNumber(toNumber);
        message.setBody("STOP");

        dataSource.sendMessage(message);

        message = new Message();
        message.setFromNumber(toNumber);
        message.setToNumber(fromNumber);
        // Note: Do not change this message value. This message is used as a flag for raise an exception for mock data.
        message.setBody("OPTOUT TEST MESSAGE");

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10005)));
        expectedException.expectMessage("to_number is opted out for messages from from_number");

        try {
            dataSource.sendMessage(message);
        } catch (MessageErrorException me) {
            throw me;
        } finally {
            message = new Message();
            message.setFromNumber(fromNumber);
            message.setToNumber(toNumber);
            message.setBody("UNSTOP");

            dataSource.sendMessage(message);
        }

    }

    @Test
    public void test_error_code_10008() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String toNumber = associatedNumbers.get(0).getPhoneNumber();

        Message message = new Message();
        message.setFromNumber("+16162000000");
        message.setToNumber(toNumber);
        message.setBody("Test,Hello!");


        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10008)));
        expectedException.expectMessage("from_number invalid, inactive, or not owned");

        dataSource.sendMessage(message);

    }

    @Test
    public void test_error_code_10009() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String toNumber = associatedNumbers.get(0).getPhoneNumber();

        Message message = new Message();
        message.setConnectorId(999999999);
        message.setToNumber(toNumber);
        message.setBody("Test,Hello!");

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10009)));
        expectedException.expectMessage("connector_id invalid, inactive, or not owned");

        dataSource.sendMessage(message);

    }

    @Test
    public void test_error_code_10012() throws VivialConnectException {

        DataSource dataSource = getDataSource();

        List<AssociatedNumber> associatedNumbers = dataSource.getLocalAssociatedNumbers();

        String fromNumber = associatedNumbers.get(0).getPhoneNumber();

        Message message = new Message();
        message.setFromNumber(fromNumber);
        message.setBody("Test,Hello!");

        expectedException.expect(MessageErrorException.class);
        expectedException.expect(Matchers.hasProperty("errorCode", Matchers.is(10012)));
        expectedException.expectMessage("Must specify to_number");

        dataSource.sendMessage(message);

    }

    @Test
    public void test_message_direction_values() throws VivialConnectException{

        DataSource dataSource = getDataSource();
        List<Message> outboundMessages;
        List<Message> inboundMessages;
        boolean comparison;

        inboundMessages =dataSource.getMessageByDirection(MessageDirection.INBOUND);
        outboundMessages =dataSource.getMessageByDirection(MessageDirection.OUTBOUND_API);

        assumeTrue("Not INBOUND and OUTBOUND messages", !inboundMessages.isEmpty()
                && !outboundMessages.isEmpty());

        comparison = outboundMessages.get(0).getDirection() == MessageDirection.OUTBOUND_API;
        assertTrue(comparison);

        comparison = inboundMessages.get(0).getDirection() == MessageDirection.INBOUND;
        assertTrue(comparison);

    }

    @Test
    public void test_message_direction_value_parsing(){

        assertSame(MessageDirection.OUTBOUND_API, MessageDirection.parseTo("outbound-api"));
        assertSame(MessageDirection.OUTBOUND_REPLY , MessageDirection.parseTo("outbound-reply"));
        assertSame(MessageDirection.INBOUND , MessageDirection.parseTo("inbound"));

    }


    private List<Message> getMessages() throws VivialConnectException {
        return getMessages(null);
    }

    private List<Message> getMessages(Map<String, String> filters) throws VivialConnectException {
        return getDataSource().getMessages(filters);
    }

    private Message getMessageById(int messageId) throws VivialConnectException {
        return getDataSource().getMessageById(messageId);
    }

    private Message getMessageById() throws VivialConnectException {
        return getDataSource().getMessageById(getMessages().get(0).getId());
    }

    private int messageCount() throws VivialConnectException {
        return getDataSource().messageCount();
    }

    private void redactMessage(Message message) throws VivialConnectException {
        getDataSource().redactMessage(message);
    }

    private List<Message> getBulkMessages(String bulkId) throws VivialConnectException {
        return getDataSource().getBulk(bulkId);
    }
}