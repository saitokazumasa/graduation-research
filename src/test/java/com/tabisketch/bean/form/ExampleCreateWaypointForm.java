package com.tabisketch.bean.form;

public class ExampleCreateWaypointForm {
    private ExampleCreateWaypointForm() {
    }

    public static CreateWaypointForm gen() {
        return new CreateWaypointForm(
                "label",
                "placeId",
                null,
                900,
                0
        );
    }
}
