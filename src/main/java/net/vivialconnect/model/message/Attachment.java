package net.vivialconnect.model.message;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;

/**
 * A MMS can contains attachments, which can be a media, text or any supported object by the API defined in the following link:
 * <a>https://dashboard.vivialconnect.net/docs/general/mms.html</a>
 * <p>
 * This class represents single message attachment and its properties.
 * <p>
 * For more info about attachments visit: <a>https://dashboard.vivialconnect.net/docs/api/attachments.html</a>
 */
@JsonRootName(value = "attachment")
public class Attachment extends VivialConnectResource {
    private static final long serialVersionUID = 4189603597882262141L;

    /**
     * Unique identifier of the media attachment object
     */
    @JsonProperty
    private int id;

    /**
     * Creation date (UTC) of the media attachment in ISO 8601 format
     */
    @JsonProperty("date_created")
    private Date dateCreated;

    /**
     * Last modification date (UTC) of the media attachment in ISO 8601 format
     */
    @JsonProperty("date_modified")
    private Date dateModified;

    /**
     * Unique identifier of your account
     */
    @JsonProperty("account_id")
    private int accountId;

    /**
     * Unique identifier of the text message for the media attachment
     */
    @JsonProperty("message_id")
    private int messageId;

    /**
     * Mime-type of the media attachment
     */
    @JsonProperty("content_type")
    private String contentType;

    /**
     * Size of the media attachment in bytes
     */
    @JsonProperty("size")
    private int size;

    /**
     * File name of the media attachment
     */
    @JsonProperty("file_name")
    private String fileName;

    /**
     * Search for a message attachment given an id.
     *
     * @param messageId    the messageId that contains the attachment
     * @param attachmentId the media attachmentId
     * @return the attachment found, or null if not found
     * @throws VivialConnectException if there is an API-level error
     */
    public static Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments/%d", messageId, attachmentId)), null, null, Attachment.class);
    }

    /**
     * Total number of attachment sent in the specified text message. If there are none, this method will return <code>0</code>
     *
     * @param messageId the messageId that contains the attachment
     * @return number of attachment in message
     * @throws VivialConnectException if there is an API-level error
     */
    public static int count(int messageId) throws VivialConnectException {
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments/count", messageId)), null, null, ResourceCount.class).getCount();
    }

    /**
     * Deletes this attachment from the database, dissociating it from the message.
     * <p>
     * If the attachment you're trying to delete does not exist, a {@link VivialConnectException}
     * holding a 404 response code will be thrown.
     *
     * @return a boolean value, indicating whether the attachment was deleted or not
     * @throws VivialConnectException if there is an API-level error
     */
    public boolean delete() throws VivialConnectException {
        try {
            request(RequestMethod.DELETE, classURLWithSuffix(Message.class, String.format("%d/attachments/%d", getMessageId(), getId())), null, null, String.class);
        } catch (NoContentException e) {
            return true;
        }

        return false;
    }

    /**
     * Unique identifier of the media attachment object
     *
     * @return unique identifier object value
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier of the media attachment object
     *
     * @param id object identifier value
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Creation date of the attachment
     *
     * @return creation date value
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Set the creation date of the attachment
     *
     * @param dateCreated creation date value
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Last modification date of the attachment.
     *
     * @return modification date value
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * Set modification date of the attachment
     *
     * @param dateModified modification date value
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * Unique identifier of your account.
     *
     * @return account ID value
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * Set the account ID value
     *
     * @param accountId account ID value
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * Unique identifier of the text message for the media attachment.
     *
     * @return message ID to which the attachment belongs
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Set the message ID of the attachment
     *
     * @param messageId message ID value
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * Mime-type of the media attachment.
     *
     * @return attachment content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Set Mime-type of the media attachment.
     *
     * @param contentType content type value
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Size of the media attachment in bytes.
     *
     * @return size of the attachment
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the size of the media attachment.
     *
     * @param size size of the media attachment.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * File name of the media attachment.
     *
     * @return file name of the attachment
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the file name of the media attachment
     *
     * @param fileName file name value
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}