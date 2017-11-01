package com.sanjyog.www.tabbedacttest.model;

import com.firebase.client.ServerValue;
import com.sanjyog.www.tabbedacttest.Constants;

import java.util.HashMap;

/**
 * Created by Sarthak on 1/12/2016.
 */
public class Message {
    private String sender, message;
   // private HashMap<String, Object> time_Sent;

    public Message() {
    }

    public Message(String sender, String message
                   ) {
        this.sender = sender;
        this.message = message;


    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }



}
