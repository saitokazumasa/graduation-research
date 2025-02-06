package com.tabisketch.exception;

public class UpdateFailedException extends Exception {
    public UpdateFailedException(final String className) {
        super(className + "の更新に失敗しました。");
    }
}
