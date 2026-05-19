package com.problems.learning.system.chat_messenger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChatServiceTest {

    private ChatService chatService;

    @BeforeEach
    void setUp() {
        chatService = new ChatService();
        chatService.registerUser("alice", "Alice");
        chatService.registerUser("bob", "Bob");
        chatService.registerUser("charlie", "Charlie");
        chatService.registerUser("dave", "Dave");
    }

    // ===================== DIRECT MESSAGE TESTS =====================

    @Test
    void createDirectChatAndSendMessages() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");
        assertThat(dm).isNotNull();
        assertThat(dm.getType()).isEqualTo(Conversation.ConversationType.DIRECT);
        assertThat(dm.getParticipantIds()).containsExactlyInAnyOrder("alice", "bob");

        chatService.sendMessage(dm.getConversationId(), "alice", "Hey Bob!");
        chatService.sendMessage(dm.getConversationId(), "bob", "Hi Alice!");

        assertThat(dm.getMessageCount()).isEqualTo(2);
        assertThat(dm.getLastMessage().getContent()).isEqualTo("Hi Alice!");
    }

    @Test
    void directChatIsNotDuplicated() {
        Conversation dm1 = chatService.getOrCreateDirectChat("alice", "bob");
        Conversation dm2 = chatService.getOrCreateDirectChat("bob", "alice"); // reversed order

        assertThat(dm1.getConversationId()).isEqualTo(dm2.getConversationId());
    }

    @Test
    void nonParticipantCannotSendInDirectChat() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");

        assertThatThrownBy(() ->
                chatService.sendMessage(dm.getConversationId(), "charlie", "Intruder!"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    // ===================== GROUP CHAT TESTS =====================

    @Test
    void createGroupChatAndSendMessages() {
        Conversation group = chatService.createGroupChat(
                "Project Team", "alice", Set.of("bob", "charlie"));

        assertThat(group.getType()).isEqualTo(Conversation.ConversationType.GROUP);
        assertThat(group.getName()).isEqualTo("Project Team");
        assertThat(group.getParticipantIds()).containsExactlyInAnyOrder("alice", "bob", "charlie");
        assertThat(group.getAdminId()).isEqualTo("alice");

        chatService.sendMessage(group.getConversationId(), "alice", "Welcome to the group!");
        chatService.sendMessage(group.getConversationId(), "bob", "Thanks!");
        chatService.sendMessage(group.getConversationId(), "charlie", "Hey everyone!");

        assertThat(group.getMessageCount()).isEqualTo(3);
    }

    @Test
    void addAndRemoveMemberFromGroup() {
        Conversation group = chatService.createGroupChat(
                "Team", "alice", Set.of("bob"));

        assertThat(group.getParticipantIds()).hasSize(2);

        // Add dave
        chatService.addToGroup(group.getConversationId(), "dave");
        assertThat(group.getParticipantIds()).hasSize(3);
        assertThat(group.isParticipant("dave")).isTrue();

        // Dave's conversations should include this group
        assertThat(chatService.getUser("dave").getConversationIds())
                .contains(group.getConversationId());

        // Remove dave
        chatService.removeFromGroup(group.getConversationId(), "dave");
        assertThat(group.isParticipant("dave")).isFalse();
    }

    @Test
    void adminCannotBeRemoved() {
        Conversation group = chatService.createGroupChat(
                "Team", "alice", Set.of("bob"));

        assertThatThrownBy(() ->
                chatService.removeFromGroup(group.getConversationId(), "alice"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("admin");
    }

    // ===================== MESSAGE RETRIEVAL TESTS =====================

    @Test
    void getLastNMessages() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");

        for (int i = 1; i <= 10; i++) {
            chatService.sendMessage(dm.getConversationId(), "alice", "Message " + i);
        }

        // Get last 3
        List<Message> last3 = chatService.getMessages(dm.getConversationId(), 3);
        assertThat(last3).hasSize(3);
        assertThat(last3.get(0).getContent()).isEqualTo("Message 8");
        assertThat(last3.get(1).getContent()).isEqualTo("Message 9");
        assertThat(last3.get(2).getContent()).isEqualTo("Message 10");
    }

    @Test
    void getLastNWhenFewerMessagesThanN() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");
        chatService.sendMessage(dm.getConversationId(), "alice", "Only one");

        List<Message> messages = chatService.getMessages(dm.getConversationId(), 100);
        assertThat(messages).hasSize(1);
    }

    @Test
    void inboxShowsAllConversationsForUser() throws InterruptedException {
        // Alice has 1 DM + 1 group
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");
        chatService.sendMessage(dm.getConversationId(), "bob", "Hello in DM");

        Thread.sleep(10); // ensure different timestamps for deterministic ordering

        Conversation group = chatService.createGroupChat(
                "Work", "alice", Set.of("charlie"));
        chatService.sendMessage(group.getConversationId(), "charlie", "Hello in group");

        List<ChatService.ConversationPreview> inbox = chatService.getInbox("alice");
        assertThat(inbox).hasSize(2);

        // Most recent conversation first (group had the latest message)
        assertThat(inbox.get(0).lastMessage().getContent()).isEqualTo("Hello in group");
        assertThat(inbox.get(1).lastMessage().getContent()).isEqualTo("Hello in DM");
    }

    @Test
    void getDirectChatBetweenUsers() {
        chatService.getOrCreateDirectChat("alice", "bob");

        Conversation found = chatService.getDirectChat("bob", "alice"); // reversed
        assertThat(found).isNotNull();
        assertThat(found.getParticipantIds()).containsExactlyInAnyOrder("alice", "bob");

        // Non-existent DM
        assertThat(chatService.getDirectChat("alice", "charlie")).isNull();
    }

    // ===================== USER STATUS TESTS =====================

    @Test
    void userOnlineOfflineStatus() {
        chatService.setOnline("alice");
        assertThat(chatService.getUser("alice").isOnline()).isTrue();

        chatService.setOffline("alice");
        assertThat(chatService.getUser("alice").isOnline()).isFalse();
    }

    @Test
    void duplicateUserRegistrationReturnsExisting() {
        User original = chatService.getUser("alice");
        User duplicate = chatService.registerUser("alice", "Alice2");
        assertThat(duplicate.getUserId()).isEqualTo(original.getUserId());
    }

    // ===================== EDGE CASES =====================

    @Test
    void emptyConversationLastMessageIsNull() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");
        assertThat(dm.getLastMessage()).isNull();
        assertThat(dm.getMessageCount()).isZero();
    }

    @Test
    void messagesReturnedInChronologicalOrder() {
        Conversation dm = chatService.getOrCreateDirectChat("alice", "bob");
        chatService.sendMessage(dm.getConversationId(), "alice", "First");
        chatService.sendMessage(dm.getConversationId(), "bob", "Second");
        chatService.sendMessage(dm.getConversationId(), "alice", "Third");

        List<Message> all = dm.getAllMessages();
        assertThat(all).hasSize(3);
        assertThat(all.get(0).getContent()).isEqualTo("First");
        assertThat(all.get(1).getContent()).isEqualTo("Second");
        assertThat(all.get(2).getContent()).isEqualTo("Third");
    }

    @Test
    void groupMessageRetrievalAfterMemberAdded() {
        Conversation group = chatService.createGroupChat(
                "Team", "alice", Set.of("bob"));

        chatService.sendMessage(group.getConversationId(), "alice", "Before dave joined");

        // Add dave — he can see messages sent before he joined
        chatService.addToGroup(group.getConversationId(), "dave");
        chatService.sendMessage(group.getConversationId(), "dave", "Just joined!");

        List<Message> all = group.getAllMessages();
        assertThat(all).hasSize(2);
        assertThat(all.get(0).getContent()).isEqualTo("Before dave joined");
        assertThat(all.get(1).getContent()).isEqualTo("Just joined!");
    }
}

