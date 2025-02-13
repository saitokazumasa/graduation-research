package com.tabisketch.bean.form;

public class ExampleEditEmailForm {
    private ExampleEditEmailForm() {}

    public static EditEmailForm gen() {
        return new EditEmailForm(
                "example2@example.com",
                "password"
        );
    }
}
