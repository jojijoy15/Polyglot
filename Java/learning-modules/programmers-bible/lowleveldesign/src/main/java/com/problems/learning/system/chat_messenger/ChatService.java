package com.problems.learning.system.chat_messenger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
    Central orchestrator for the Chat Messenger system.

    Data Structures:
        ┌──────────────────────────────────────────────────────────────────────────┐
        │  HashMap<userId, User>              → O(1) user lookup                  │
        │  HashMap<conversationId, Conversation> → O(1) conversation lookup       │
        │  HashMap<"u1:u2", conversationId>   → O(1) direct chat dedup            │
        │  User.conversationIds (Set)         → O(1) check membership             │
        │  Deque<Message> per Conversation    → O(1) send, O(N) retrieve last N   │
        └──────────────────────────────────────────────────────────────────────────┘

    Quick Retrieval Strategy:
        - Messages per user: user.conversationIds → get each conversation → getLastNMessages()
        - Messages per group: conversationId → conversation.getLastNMessages()
        - Direct chat between two users: directChatKey → conversationId → messages
*/
public class ChatService {

    private final Map<String, User> users;
    private final Map<String, Conversation> conversations;
    // Key: "userId1:userId2" (sorted) → conversationId. Prevents duplicate DMs.
    private final Map<String, String> directChatIndex;
    private int conversationCounter;

    public ChatService() {
        this.users = new ConcurrentHashMap<>();
        this.conversations = new ConcurrentHashMap<>();
        this.directChatIndex = new ConcurrentHashMap<>();
        this.conversationCounter = 0;
    }

    // ===================== USER MANAGEMENT =====================

    public User registerUser(String userId, String displayName) {
        if (users.containsKey(userId)) {
            System.out.println("⚠️ User " + userId + " already exists.");
            return users.get(userId);
        }
        User user = new User(userId, displayName);
        users.put(userId, user);
        System.out.println("✅ Registered: " + user);
        return user;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public void setOnline(String userId) {
        User user = users.get(userId);
        if (user != null) {
            user.setOnline(true);
            System.out.println("🟢 " + user.getDisplayName() + " is online.");
        }
    }

    public void setOffline(String userId) {
        User user = users.get(userId);
        if (user != null) {
            user.setOnline(false);
            System.out.println("⚪ " + user.getDisplayName() + " is offline.");
        }
    }

    // ===================== DIRECT MESSAGE =====================

    /*
        Start or retrieve a 1:1 conversation between two users.
        Uses directChatIndex to prevent duplicates.

        Time: O(1) — HashMap lookup/insert
    */
    public Conversation getOrCreateDirectChat(String userId1, String userId2) {
        validateUsers(userId1, userId2);

        String key = directChatKey(userId1, userId2);
        if (directChatIndex.containsKey(key)) {
            return conversations.get(directChatIndex.get(key));
        }

        String conversationId = "DM-" + (++conversationCounter);
        Conversation conversation = new Conversation(conversationId, userId1, userId2);
        conversations.put(conversationId, conversation);
        directChatIndex.put(key, conversationId);

        users.get(userId1).addConversation(conversationId);
        users.get(userId2).addConversation(conversationId);

        System.out.println("💬 Direct chat created: " + userId1 + " ↔ " + userId2
                + " (" + conversationId + ")");
        return conversation;
    }

    // ===================== GROUP CHAT =====================

    /*
        Create a group chat with an admin and initial members.
        Time: O(M) where M = number of members
    */
    public Conversation createGroupChat(String groupName, String adminId, Set<String> memberIds) {
        if (!users.containsKey(adminId)) {
            throw new IllegalArgumentException("Admin user not found: " + adminId);
        }
        for (String memberId : memberIds) {
            if (!users.containsKey(memberId)) {
                throw new IllegalArgumentException("Member not found: " + memberId);
            }
        }

        String conversationId = "GRP-" + (++conversationCounter);
        Conversation group = new Conversation(conversationId, groupName, adminId, memberIds);
        conversations.put(conversationId, group);

        // Link conversation to all participants
        for (String memberId : group.getParticipantIds()) {
            users.get(memberId).addConversation(conversationId);
        }

        System.out.println("👥 Group '" + groupName + "' created (" + conversationId
                + ") with " + group.getParticipantIds().size() + " members.");
        return group;
    }

    /*
        Add a member to an existing group.
    */
    public void addToGroup(String conversationId, String userId) {
        Conversation group = getGroupConversation(conversationId);
        if (!users.containsKey(userId)) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        group.addParticipant(userId);
        users.get(userId).addConversation(conversationId);
        System.out.println("➕ " + users.get(userId).getDisplayName() + " added to " + group.getName());
    }

    /*
        Remove a member from a group. Admin cannot be removed.
    */
    public void removeFromGroup(String conversationId, String userId) {
        Conversation group = getGroupConversation(conversationId);
        group.removeParticipant(userId);
        users.get(userId).removeConversation(conversationId);
        System.out.println("➖ " + users.get(userId).getDisplayName() + " removed from " + group.getName());
    }

    // ===================== MESSAGING =====================

    /*
        Send a message in a conversation (direct or group).
        Time: O(1) — Deque addLast
    */
    public Message sendMessage(String conversationId, String senderId, String content) {
        Conversation conversation = conversations.get(conversationId);
        if (conversation == null) {
            throw new IllegalArgumentException("Conversation not found: " + conversationId);
        }
        if (!users.containsKey(senderId)) {
            throw new IllegalArgumentException("User not found: " + senderId);
        }

        Message message = new Message(senderId, content);
        conversation.addMessage(message);
        System.out.println("📨 [" + conversationId + "] " + users.get(senderId).getDisplayName()
                + ": " + content);
        return message;
    }

    // ===================== MESSAGE RETRIEVAL =====================

    /*
        Get last N messages from a specific conversation.
        Time: O(N) — iterate Deque from tail
    */
    public List<Message> getMessages(String conversationId, int lastN) {
        Conversation conversation = conversations.get(conversationId);
        if (conversation == null) return List.of();
        return conversation.getLastNMessages(lastN);
    }

    /*
        Get all conversations for a user with their last message.
        This is the "inbox" view.

        Time: O(C) where C = number of conversations for this user
    */
    public List<ConversationPreview> getInbox(String userId) {
        User user = users.get(userId);
        if (user == null) return List.of();

        List<ConversationPreview> inbox = new ArrayList<>();
        for (String convId : user.getConversationIds()) {
            Conversation conv = conversations.get(convId);
            if (conv != null) {
                Message lastMsg = conv.getLastMessage();
                inbox.add(new ConversationPreview(conv, lastMsg));
            }
        }

        // Sort by most recent message first
        inbox.sort((a, b) -> {
            if (a.lastMessage() == null) return 1;
            if (b.lastMessage() == null) return -1;
            return b.lastMessage().getTimestamp().compareTo(a.lastMessage().getTimestamp());
        });

        return inbox;
    }

    /*
        Get the direct chat between two users (if exists).
        Time: O(1)
    */
    public Conversation getDirectChat(String userId1, String userId2) {
        String key = directChatKey(userId1, userId2);
        String convId = directChatIndex.get(key);
        return (convId != null) ? conversations.get(convId) : null;
    }

    public Conversation getConversation(String conversationId) {
        return conversations.get(conversationId);
    }

    // ===================== HELPERS =====================

    private String directChatKey(String userId1, String userId2) {
        // Sort to ensure "alice:bob" == "bob:alice"
        return (userId1.compareTo(userId2) < 0)
                ? userId1 + ":" + userId2
                : userId2 + ":" + userId1;
    }

    private void validateUsers(String... userIds) {
        for (String userId : userIds) {
            if (!users.containsKey(userId)) {
                throw new IllegalArgumentException("User not found: " + userId);
            }
        }
    }

    private Conversation getGroupConversation(String conversationId) {
        Conversation conv = conversations.get(conversationId);
        if (conv == null) throw new IllegalArgumentException("Conversation not found: " + conversationId);
        if (conv.getType() != Conversation.ConversationType.GROUP) {
            throw new IllegalArgumentException(conversationId + " is not a group chat");
        }
        return conv;
    }

    /*
        Preview record for inbox display — conversation + its last message.
    */
    public record ConversationPreview(Conversation conversation, Message lastMessage) {
        @Override
        public String toString() {
            String label = (conversation.getType() == Conversation.ConversationType.GROUP)
                    ? conversation.getName()
                    : conversation.getConversationId();
            String preview = (lastMessage != null)
                    ? lastMessage.getSenderId() + ": " + lastMessage.getContent()
                    : "(no messages)";
            return label + " — " + preview;
        }
    }
}

