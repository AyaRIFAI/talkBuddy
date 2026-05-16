package io.lingua.talkbuddy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;
    private LocalDateTime startDayTime;
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    public ConversationEntity() {
    }

    public ConversationEntity(LocalDateTime startDayTime, UserEntity user) {
        this.startDayTime = startDayTime;
        this.user = user;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public LocalDateTime getStartDayTime() {
        return startDayTime;
    }

    public void setStartDayTime(LocalDateTime startDayTime) {
        this.startDayTime = startDayTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
