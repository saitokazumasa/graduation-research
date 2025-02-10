package com.tabisketch.bean.form;

public class ExampleResetPasswordForm {
    private ExampleResetPasswordForm() {}

    public static ResetPasswordForm gen() {
        return new ResetPasswordForm(
                "password",
                "password"
        );
    }
}
