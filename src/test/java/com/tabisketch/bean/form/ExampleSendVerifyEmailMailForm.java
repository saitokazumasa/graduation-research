package com.tabisketch.bean.form;

public class ExampleSendVerifyEmailMailForm {
    private ExampleSendVerifyEmailMailForm() {
    }

    public static SendVerifyEmailMailFrom gen() {
        return new SendVerifyEmailMailFrom(
                "example@example.com"
        );
    }
}
