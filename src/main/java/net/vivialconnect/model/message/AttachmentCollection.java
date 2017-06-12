package net.vivialconnect.model.message;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AttachmentCollection{
	
    @JsonProperty
    private List<Attachment> attachments;


    public List<Attachment> getAttachments(){
        return attachments;
    }


    public void setAttachments(List<Attachment> attachments){
        this.attachments = attachments;
    }
}