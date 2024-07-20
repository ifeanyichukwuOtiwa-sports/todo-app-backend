package iwo.wintech.todoapp.api.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXIST("User already exists");

    private final String message;

    public RuntimeException exception() {
        return new RuntimeException(message);
    }
}
