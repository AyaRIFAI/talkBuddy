package io.lingua.talkbuddy.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ConversationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ConversationEntity() {
    }

    public ConversationEntity(LocalDateTime createdAt, UserEntity user) {
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}
