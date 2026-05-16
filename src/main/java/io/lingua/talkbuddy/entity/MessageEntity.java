package io.lingua.talkbuddy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String content;
    private LocalDateTime dateAndTime;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;
    public MessageEntity() {
    }

    public MessageEntity( String author, String content, ConversationEntity conversation, LocalDateTime dateAndTime) {
        this.author = author;
        this.content = content;
        this.conversation = conversation;
        this.dateAndTime = dateAndTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }
}