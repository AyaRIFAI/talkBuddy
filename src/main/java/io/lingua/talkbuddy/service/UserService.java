package io.lingua.talkbuddy.service;

import io.lingua.talkbuddy.dto.CreateUserRequest;
import io.lingua.talkbuddy.dto.UserResponse;
import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.mapper.UserMapper;
import io.lingua.talkbuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        UserEntity user = userMapper.toEntity(createUserRequest);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);

    }
}
