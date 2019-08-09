package net.vivialconnect.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.vivialconnect.model.message.Message;
import net.vivialconnect.model.message.Attachment;
import net.vivialconnect.model.message.BulkInfo;
import net.vivialconnect.model.message.BulkInfoCollection;
import net.vivialconnect.model.message.BulkMessage;

import org.junit.Test;
import net.vivialconnect.model.error.VivialConnectException;
import net.vivialconnect.model.number.AssociatedNumber;

public class MessageTest extends BaseTestCase {

    private static final String MESSAGE_BODY = "Message from Vivial Connect Test Suite";

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
    public void test_create_bulk_message() throws VivialConnectException{

        List<AssociatedNumber> numbers = getDataSource().getAssociatedNumbers();
        String fromNumber = numbers.get(0).getPhoneNumber();
        List<String> bulkSendNumberList = new ArrayList<String>();

        for(AssociatedNumber number: numbers.subList(1, numbers.size())){
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
    public void test_get_bulks_sent() throws VivialConnectException{

        BulkInfoCollection bulksSent = getDataSource().getCreatedBulks();
        List<BulkInfo> bulks = bulksSent.getBulkList();
        String someBulkId = bulks.get(0).getBulkId();

        List<Message> bulkMessages = getBulkMessages(someBulkId);

        assertNotNull(bulkMessages);
        assertTrue("Bulks count is zero", bulksSent.getCount() > 0);
        assertTrue("Bulk pages is zero", bulksSent.getPages() > 0);
        assertFalse("Bulk message list is empty", bulkMessages.isEmpty());

        for(Message message: bulkMessages){
            assertNotNull(String.format("Message %d has a null bulk ID",message.getId()),message.getBulkId());
        }
    }

    @Test(expected = VivialConnectException.class)
    public void test_send_bulk_empty_to_numbers() throws VivialConnectException {

        BulkMessage bulkMessage = new BulkMessage();
        bulkMessage.setBody("Testing Bulk send...");

        getDataSource().sendBulkWithoutNumbers(bulkMessage);
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