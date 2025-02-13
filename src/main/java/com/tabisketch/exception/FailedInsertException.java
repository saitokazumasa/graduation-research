package com.tabisketch.exception;

public class FailedInsertException extends RuntimeException {
    public FailedInsertException(final String message) {
        super(message);
    }
}
