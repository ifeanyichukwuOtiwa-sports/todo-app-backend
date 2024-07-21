package iwo.wintech.todoapp.rest.controller;

import iwo.wintech.todoapp.api.dto.UserResponse;
import iwo.wintech.todoapp.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@RestController
class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

}
