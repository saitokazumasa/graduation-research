package com.tabisketch.bean.form;

public class ExampleCreateDestinationPointForm {
    private ExampleCreateDestinationPointForm() {
    }

    public static CreateDestinationPointForm gen() {
        return new CreateDestinationPointForm(
                "label",
                "placeId"
        );
    }
}
