package com.tabisketch.bean.form;

public class ExampleCreateUserForm {
    private ExampleCreateUserForm() {
    }

    public static CreateUserForm gen() {
        return new CreateUserForm(
                "example@example.com",
                "password",
                "password"
        );
    }
}
