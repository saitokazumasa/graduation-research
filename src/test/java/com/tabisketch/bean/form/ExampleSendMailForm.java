package com.tabisketch.bean.form;

public class ExampleSendMailForm {
    private ExampleSendMailForm() {
    }

    public static SendMailForm gen(final String from, final String to) {
        return new SendMailForm(
                from,
                to,
                "test mail",
                "test mail"
        );
    }
}
