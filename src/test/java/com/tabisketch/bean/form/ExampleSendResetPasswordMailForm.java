package com.tabisketch.bean.form;

public class ExampleSendResetPasswordMailForm {
    private ExampleSendResetPasswordMailForm() {}

    public static SendResetPasswordMailForm gen() {
        return new SendResetPasswordMailForm(
                "example@example.com"
        );
    }
}
