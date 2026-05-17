package io.lingua.talkbuddy.service;

import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.model.User;
import io.lingua.talkbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User createUser(User user){
        UserEntity saved=  userRepository.save(new UserEntity(user.getName(), user.getMail(), user.getLanguage(), user.getNativeLanguage()));
        return new User(saved.getUserId(), saved.getName(), saved.getMail(), saved.getLanguageToLearn(), saved.getNativeLanguage());

    }
}
