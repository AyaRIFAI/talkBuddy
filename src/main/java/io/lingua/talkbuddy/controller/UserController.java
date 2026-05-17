package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.model.MessageToClient;
import io.lingua.talkbuddy.model.User;
import io.lingua.talkbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/user/new")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
         return userService.createUser(user);

    }
}
