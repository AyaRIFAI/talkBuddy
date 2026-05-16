package io.lingua.talkbuddy.repository;

import io.lingua.talkbuddy.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
}
