package io.lingua.talkbuddy.entity;

import io.lingua.talkbuddy.dto.Correction;
import io.lingua.talkbuddy.model.Sender;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Sender sender;
    private String content;
    private String correctedText;
    private String correctionExplanation;
    private String llmResponseTranslation;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

    public MessageEntity() {
    }

    public MessageEntity(Sender sender, String content, String correctedText, String correctionExplanation,String llmResponseTranslation, ConversationEntity conversation, LocalDateTime createdAt) {
        this.sender = sender;
        this.content = content;
        this.correctedText = correctedText;
        this.correctionExplanation=correctionExplanation;
        this.llmResponseTranslation=llmResponseTranslation;
        this.conversation = conversation;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }

    public void setLlmResponseTranslation(String llmResponseTranslation) {
        this.llmResponseTranslation = llmResponseTranslation;
    }
    public String getCorrectedText() {
        return correctedText;
    }

    public void setCorrectedText(String correctedText) {
        this.correctedText = correctedText;
    }

    public String getCorrectionExplanation() {
        return correctionExplanation;
    }

    public void setCorrectionExplanation(String correctionExplanation) {
        this.correctionExplanation = correctionExplanation;
    }
    public String getLlmResponseTranslation() {
        return llmResponseTranslation;
    }
}
