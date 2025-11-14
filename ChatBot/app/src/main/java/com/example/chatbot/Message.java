package com.example.chatbot;

public class Message {
    public String text;
    public boolean isUser;

    public Message(String text, boolean isUser) {
        this.text = text;
        this.isUser = isUser;
    }
}
