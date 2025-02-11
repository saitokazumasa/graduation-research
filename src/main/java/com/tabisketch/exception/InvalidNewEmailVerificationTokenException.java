package com.tabisketch.exception;

public class InvalidNewEmailVerificationTokenException extends RuntimeException {
    public InvalidNewEmailVerificationTokenException(final String message) {
        super(message);
    }
}
