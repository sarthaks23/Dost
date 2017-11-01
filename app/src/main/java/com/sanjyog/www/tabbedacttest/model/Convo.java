package com.sanjyog.www.tabbedacttest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.sanjyog.www.tabbedacttest.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sarthak on 12/27/2015.
 */
public class Convo {
    private String convo_Name;
    private String owner;
    private HashMap<String, Object> timestamp;

    public Convo() {
    }

    public Convo(String convo_Name, String owner ,HashMap<String, Object> timestamp) {
        this.convo_Name = convo_Name;
        this.owner = owner;

        HashMap<String, Object> TimeStamp = new HashMap<String, Object>();
        TimeStamp.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestamp = TimeStamp;

    }

    public String getConvo_Name() {
        return convo_Name;
    }

    public String getOwner() {
        return owner;
    }

    public HashMap<String, Object> getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public long getTimestampLong(){
        return (long)timestamp.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

}
