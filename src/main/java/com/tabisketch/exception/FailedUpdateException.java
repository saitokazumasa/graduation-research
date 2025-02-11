package com.tabisketch.exception;

public class FailedUpdateException extends RuntimeException {
    public FailedUpdateException(final String message) {
        super(message);
    }
}
