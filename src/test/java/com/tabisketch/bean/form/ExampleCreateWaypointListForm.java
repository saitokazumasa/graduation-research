package com.tabisketch.bean.form;

import java.util.UUID;
import java.util.stream.Stream;

public class ExampleCreateWaypointListForm {
    private ExampleCreateWaypointListForm() {
    }

    public static CreateWaypointListForm gen() {
        return new CreateWaypointListForm(
                1,
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                ExampleCreateDeparturePointForm.gen(),
                Stream.of(ExampleCreateWaypointForm.gen()).toList(),
                ExampleCreateDestinationPointForm.gen()
        );
    }
}
