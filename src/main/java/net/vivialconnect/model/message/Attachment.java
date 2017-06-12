package net.vivialconnect.model.message;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import net.vivialconnect.model.ResourceCount;
import net.vivialconnect.model.VivialConnectResource;
import net.vivialconnect.model.error.NoContentException;
import net.vivialconnect.model.error.VivialConnectException;

@JsonRootName(value = "attachment")
public class Attachment extends VivialConnectResource{
    private static final long serialVersionUID = 4189603597882262141L;

    /** Unique identifier of the media attachment object */
    @JsonProperty
    private int id;

    /** Creation date (UTC) of the media attachment in ISO 8601 format */
    @JsonProperty("date_created")
    private Date dateCreated;

    /** Last modification date (UTC) of the media attachment in ISO 8601 format */
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
     * @param messageId the messageId that contains the attachment
     * @param attachmentId the media attachmentId
     * 
     * @return the attachment found, or null if not found
     * @throws VivialConnectException if there is an API-level error
     * 
     *
     */
    public static Attachment getAttachmentById(int messageId, int attachmentId) throws VivialConnectException{
        return request(RequestMethod.GET, classURLWithSuffix(Message.class, String.format("%d/attachments/%d", messageId, attachmentId)), null, null, Attachment.class);
    }

    /**
     * Total number of attachment sent in the specified text message. If there are none, this method will return <code>0</code>
     * 
     * @param messageId the messageId that contains the attachment
     * 
     * @return number of attachment in message
     * @throws VivialConnectException if there is an API-level error
     *
     */
    public static int count(int messageId) throws VivialConnectException{
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
     *
     */
    public boolean delete() throws VivialConnectException{
        try{
            request(RequestMethod.DELETE, classURLWithSuffix(Message.class, String.format("%d/attachments/%d", getMessageId(), getId())), null, null, String.class);
        }catch(NoContentException e){
            return true;
        }

        return false;
    }


    public int getId(){
        return id;
    }


    public void setId(int id){
        this.id = id;
    }


    public Date getDateCreated(){
        return dateCreated;
    }


    public void setDateCreated(Date dateCreated){
        this.dateCreated = dateCreated;
    }


    public Date getDateModified(){
        return dateModified;
    }


    public void setDateModified(Date dateModified){
        this.dateModified = dateModified;
    }


    public int getAccountId(){
        return accountId;
    }


    public void setAccountId(int accountId){
        this.accountId = accountId;
    }


    public int getMessageId(){
        return messageId;
    }


    public void setMessageId(int messageId){
        this.messageId = messageId;
    }


    public String getContentType(){
        return contentType;
    }


    public void setContentType(String contentType){
        this.contentType = contentType;
    }


    public int getSize(){
        return size;
    }


    public void setSize(int size){
        this.size = size;
    }


    public String getFileName(){
        return fileName;
    }


    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}