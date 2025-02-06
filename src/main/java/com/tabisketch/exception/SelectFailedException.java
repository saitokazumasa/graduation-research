package com.tabisketch.exception;

public class SelectFailedException extends Exception {
    public SelectFailedException(final String className) {
        super(className + "の取得に失敗しました。");
    }
}
