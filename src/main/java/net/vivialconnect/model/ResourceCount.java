package net.vivialconnect.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceCount{
	
    @JsonProperty
    private int count;


    public int getCount(){
        return count;
    }


    public void setCount(int count){
        this.count = count;
    }
}