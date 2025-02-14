package com.tabisketch.bean.form;

import java.time.LocalDateTime;

public class ExampleCreateDeparturePointForm {
    private ExampleCreateDeparturePointForm() {
    }

    public static CreateDeparturePointForm gen() {
        return new CreateDeparturePointForm(
                "label",
                "placeId",
                LocalDateTime.now()
        );
    }
}
