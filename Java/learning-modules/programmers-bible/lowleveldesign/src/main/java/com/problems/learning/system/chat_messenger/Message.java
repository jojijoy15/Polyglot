package com.problems.learning.system.chat_messenger;

import java.time.LocalDateTime;
import java.util.UUID;

/*
    Represents a chat message.
    Immutable — once sent, content doesn't change.
*/
public class Message {

    private final String messageId;
    private final String senderId;
    private final String content;
    private final LocalDateTime timestamp;

    public Message(String senderId, String content) {
        this.messageId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.senderId = senderId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // For testing with custom timestamp
    public Message(String senderId, String content, LocalDateTime timestamp) {
        this.messageId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.senderId = senderId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp.toLocalTime() + "] " + senderId + ": " + content;
    }
}

