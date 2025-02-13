package com.tabisketch.bean.form;

public class ExampleEditPasswordForm {
    private ExampleEditPasswordForm() {
    }

    public static EditPasswordForm gen() {
        return new EditPasswordForm(
                "password",
                "newPassword",
                "newPassword"
        );
    }
}
