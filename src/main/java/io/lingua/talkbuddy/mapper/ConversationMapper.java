package io.lingua.talkbuddy.mapper;

import io.lingua.talkbuddy.dto.ConversationResponse;
import io.lingua.talkbuddy.dto.CreateConversationRequest;
import io.lingua.talkbuddy.entity.ConversationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationEntity toEntity(CreateConversationRequest request);
    ConversationResponse toResponse(ConversationEntity entity);
}