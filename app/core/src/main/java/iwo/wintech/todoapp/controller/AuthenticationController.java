package iwo.wintech.todoapp.controller;

import iwo.wintech.todoapp.model.User;
import iwo.wintech.todoapp.service.CreateUserRequest;
import iwo.wintech.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserService userService;


    @PostMapping("/register")
    public boolean registerUser(@RequestBody final CreateUserRequest request) {
        final User user = userService.createUser(request);
        return Objects.nonNull(user);
    }


}
