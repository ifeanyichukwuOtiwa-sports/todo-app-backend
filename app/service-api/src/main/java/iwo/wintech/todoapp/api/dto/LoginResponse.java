package iwo.wintech.todoapp.api.dto;

public record LoginResponse(
        String token,
        String firstName,
        String lastName,
        String userId
) {
}
