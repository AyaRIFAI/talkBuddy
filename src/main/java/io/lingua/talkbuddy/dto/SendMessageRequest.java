package io.lingua.talkbuddy.dto;

public record SendMessageRequest(String content) {
}
/*public class MessageFromClient {
    private String message;
    public String getMessage() {
        return message;
    }

    public MessageFromClient() {}
    public MessageFromClient(Long conversationId) {
        this.conversationId=conversationId;
    }
    public MessageFromClient(String message, Long conversationId) {
        this.message=message;
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
}*/
