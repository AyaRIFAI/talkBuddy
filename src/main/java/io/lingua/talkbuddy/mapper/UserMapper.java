package io.lingua.talkbuddy.mapper;

import io.lingua.talkbuddy.dto.CreateUserRequest;
import io.lingua.talkbuddy.dto.UserResponse;
import io.lingua.talkbuddy.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserEntity toEntity(CreateUserRequest request);

    public UserResponse toResponse(UserEntity entity);
}