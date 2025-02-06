package com.tabisketch.exception;

public class InvalidMailAddressException extends Exception {
    public InvalidMailAddressException() {
        super("無効なメールアドレスです。");
    }
}
