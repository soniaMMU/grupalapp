package com.cauquitas.everythingappbd;

public class Message {
    private String sender;
    private String text;
    private String timestamp;

    public Message(String sender, String text, String timestamp) {
        this.sender = sender;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}