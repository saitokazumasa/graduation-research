package com.tabisketch.exception;

public class FailedDeleteException extends RuntimeException {
    public FailedDeleteException(final String message) {
        super(message);
    }
}
