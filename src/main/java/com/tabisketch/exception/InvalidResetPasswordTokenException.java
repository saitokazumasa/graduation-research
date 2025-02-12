package com.tabisketch.exception;

public class InvalidResetPasswordTokenException extends RuntimeException {
    public InvalidResetPasswordTokenException(final String message) {
        super(message);
    }
}
