package iwo.wintech.todoapp.service;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String password
) {
}
