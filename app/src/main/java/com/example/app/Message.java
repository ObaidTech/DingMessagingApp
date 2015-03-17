package com.example.app;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by OBNinja on 11/9/14.
 */
public class Message {
    private String content;
    private String senderName;
    private GregorianCalendar sentOn;

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSentOn(GregorianCalendar sentOn) {
        this.sentOn = sentOn;
    }

    public Message(String aContent, String aSenderName, GregorianCalendar asentOn){
        setContent(aContent);
        setSenderName(aSenderName);
        setSentOn(asentOn);
    }

    public GregorianCalendar getSentOn() {
        return sentOn;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }
}
