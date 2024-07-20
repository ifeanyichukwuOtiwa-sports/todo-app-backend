package iwo.wintech.todoapp.api.service;

import iwo.wintech.todoapp.api.dto.CreateUserRequest;
import iwo.wintech.todoapp.api.dto.UserResponse;
import iwo.wintech.todoapp.persistence.model.User;

import java.util.List;

public interface UserService {
    boolean createUser(CreateUserRequest request);
    UserResponse findUserByEmailOrUsername(String emailOrUsername);
    UserResponse findUserById(String userId);
    List<UserResponse> getAllUsers();

    User findUser(String username);
}
