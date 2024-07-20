package iwo.wintech.todoapp.api.dto;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String username,
        String password
) {
}
