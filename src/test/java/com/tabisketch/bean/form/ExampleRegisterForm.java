package com.tabisketch.bean.form;

public class ExampleRegisterForm {
    private ExampleRegisterForm() {
    }

    public static RegisterForm gen() {
        return new RegisterForm(
                "example@example.com",
                "password",
                "password"
        );
    }
}
