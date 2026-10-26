package io.lingua.talkbuddy.mapper;


import io.lingua.talkbuddy.dto.SendMessageRequest;
import io.lingua.talkbuddy.dto.MessageResponse;
import io.lingua.talkbuddy.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageEntity toEntity(SendMessageRequest request);
    @Mapping(target = "correction.correctedText", source = "correctedText")
    @Mapping(target = "correction.explanation", source = "correctionExplanation")
    @Mapping(target = "conversationId", source = "conversation.id")
    MessageResponse toResponse(MessageEntity entity);
}
