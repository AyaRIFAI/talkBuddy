package io.lingua.talkbuddy.dto;

import java.time.LocalDateTime;

public record ConversationResponse(Long id, LocalDateTime createdAt) {
}
