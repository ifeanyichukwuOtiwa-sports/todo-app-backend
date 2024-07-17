package iwo.wintech.todoapp.service;

import java.time.LocalDateTime;

public record UserResponse(
        String userId,
        String firstName,
        String lastName,
        String email,
        String username,
        LocalDateTime createdAt
) {
}
