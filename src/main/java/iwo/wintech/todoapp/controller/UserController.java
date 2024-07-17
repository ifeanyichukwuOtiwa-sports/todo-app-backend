package iwo.wintech.todoapp.controller;

import iwo.wintech.todoapp.model.User;
import iwo.wintech.todoapp.service.UserResponse;
import iwo.wintech.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }
}
