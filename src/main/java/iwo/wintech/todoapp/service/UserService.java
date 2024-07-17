package iwo.wintech.todoapp.service;

import iwo.wintech.todoapp.model.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequest request);
    User findUserByEmailOrUsername(String emailOrUsername);
    User findUserById(String userId);
    List<UserResponse> getAllUsers();
}
