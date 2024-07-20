package iwo.wintech.todoapp.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        String firstName,
        String lastName,

        @NotBlank
        @Email
        String email,

        String username,
        @NotBlank
        String password
) {
}
