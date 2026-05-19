package com.problems.learning.system.chat_messenger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
    Represents a user in the chat system.

    Each user has:
        - Unique userId
        - Display name
        - Online/offline status
        - Set of conversation IDs they participate in (for quick lookup)
*/
public class User {

    private final String userId;
    private String displayName;
    private boolean online;
    private final Set<String> conversationIds; // conversation IDs this user is part of

    public User(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
        this.online = false;
        this.conversationIds = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Set<String> getConversationIds() {
        return Set.copyOf(conversationIds);
    }

    public void addConversation(String conversationId) {
        conversationIds.add(conversationId);
    }

    public void removeConversation(String conversationId) {
        conversationIds.remove(conversationId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return displayName + " (" + userId + ")" + (online ? " 🟢" : " ⚪");
    }
}

