package com.problems.learning.system.chat_messenger;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/*
    Represents a conversation (chat thread) between users.
    Can be either a 1:1 direct message or a group chat.

    Data Structure:
        Deque<Message> — messages stored in insertion order.
        ConcurrentLinkedDeque for thread-safe append and iteration.
        - addLast() → O(1) send
        - descendingIterator() → latest messages first (quick retrieval)
        - getLastN() → retrieve N most recent messages efficiently

    Why Deque over List?
        - O(1) append at tail (send message)
        - O(1) access to most recent message (peekLast)
        - Efficient reverse iteration without sorting
*/
public class Conversation {

    public enum ConversationType {
        DIRECT,  // 1:1 chat between two users
        GROUP    // group chat with multiple users
    }

    private final String conversationId;
    private String name; // group name or null for direct chats
    private final ConversationType type;
    private final Set<String> participantIds;
    private String adminId; // only for GROUP type
    private final Deque<Message> messages;

    // Direct message constructor
    public Conversation(String conversationId, String user1Id, String user2Id) {
        this.conversationId = conversationId;
        this.type = ConversationType.DIRECT;
        this.participantIds = new LinkedHashSet<>();
        this.participantIds.add(user1Id);
        this.participantIds.add(user2Id);
        this.messages = new ConcurrentLinkedDeque<>();
        this.name = null;
        this.adminId = null;
    }

    // Group chat constructor
    public Conversation(String conversationId, String name, String adminId, Set<String> memberIds) {
        this.conversationId = conversationId;
        this.type = ConversationType.GROUP;
        this.name = name;
        this.adminId = adminId;
        this.participantIds = new LinkedHashSet<>(memberIds);
        this.participantIds.add(adminId); // admin is always a participant
        this.messages = new ConcurrentLinkedDeque<>();
    }

    /*
        Send a message to this conversation.
        Time: O(1) — Deque.addLast()
    */
    public void addMessage(Message message) {
        if (!participantIds.contains(message.getSenderId())) {
            throw new IllegalArgumentException("User " + message.getSenderId() + " is not a participant");
        }
        messages.addLast(message);
    }

    /*
        Get the N most recent messages (latest first).
        Time: O(N) — iterate from tail
    */
    public List<Message> getLastNMessages(int n) {
        List<Message> result = new ArrayList<>();
        Iterator<Message> it = messages.descendingIterator();
        while (it.hasNext() && result.size() < n) {
            result.add(it.next());
        }
        Collections.reverse(result); // return in chronological order
        return result;
    }

    /*
        Get all messages in chronological order.
    */
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages);
    }

    /*
        Get the most recent message.
        Time: O(1) — Deque.peekLast()
    */
    public Message getLastMessage() {
        return messages.peekLast();
    }

    public int getMessageCount() {
        return messages.size();
    }

    // Group management
    public void addParticipant(String userId) {
        participantIds.add(userId);
    }

    public void removeParticipant(String userId) {
        if (userId.equals(adminId)) {
            throw new IllegalArgumentException("Cannot remove the admin. Transfer admin first.");
        }
        participantIds.remove(userId);
    }

    public boolean isParticipant(String userId) {
        return participantIds.contains(userId);
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConversationType getType() {
        return type;
    }

    public Set<String> getParticipantIds() {
        return Set.copyOf(participantIds);
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        String label = (type == ConversationType.GROUP) ? "Group[" + name + "]" : "DM";
        return label + " (" + conversationId + ") — " + participantIds.size()
                + " members, " + messages.size() + " messages";
    }
}

