package iwo.wintech.todoapp.api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String username,

        @NotBlank
        String password,
        boolean rememberMe
) {
}
