package com.tabisketch.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("無効なパスワードです。");
    }
}
