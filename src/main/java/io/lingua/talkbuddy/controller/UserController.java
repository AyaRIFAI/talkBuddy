package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.dto.CreateUserRequest;
import io.lingua.talkbuddy.dto.CreateUserResponse;
import io.lingua.talkbuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user);

    }
}
