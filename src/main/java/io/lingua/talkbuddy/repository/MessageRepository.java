package io.lingua.talkbuddy.repository;

import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByConversationOrderByDateAndTime(ConversationEntity conversation);
}
