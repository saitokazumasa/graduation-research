package com.tabisketch.exception;

public class InvalidEmailVerificationTokenException extends RuntimeException {
    public InvalidEmailVerificationTokenException(final String message) {
        super(message);
    }
}
