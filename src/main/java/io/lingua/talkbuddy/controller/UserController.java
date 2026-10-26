package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.dto.CreateUserRequest;
import io.lingua.talkbuddy.dto.UserResponse;
import io.lingua.talkbuddy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);

    }
}
