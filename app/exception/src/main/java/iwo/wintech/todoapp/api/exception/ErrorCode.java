package iwo.wintech.todoapp.api.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXIST("User already exists"),
    WRONG_PASSWORD("Invalid password"),
    SESSION_EXPIRED("User session has been Expired"),
    SESSION_INVALID("User session has been invalidated");

    private final String message;

    public RuntimeException exception() {
        return new RuntimeException(message);
    }
}
