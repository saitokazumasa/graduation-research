package com.tabisketch.exception;

public class InsertFailedException extends Exception {
    public InsertFailedException(final String className) {
        super(className + "の追加に失敗しました。");
    }
}
