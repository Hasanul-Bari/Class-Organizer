package com.example.gridlayout;

public class MessageItem {

    private String message,sender,time,uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MessageItem(String message, String sender, String time, String uid) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.uid= uid;
    }

    public MessageItem(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
