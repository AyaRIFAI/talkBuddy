package io.lingua.talkbuddy.model;

public class MessageFromClient {
    private String message;
    private Long conversationId;
    public String getMessage() {
        return message;
    }

    public MessageFromClient() {}
    public MessageFromClient(Long conversationId) {
        this.conversationId=conversationId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
}
