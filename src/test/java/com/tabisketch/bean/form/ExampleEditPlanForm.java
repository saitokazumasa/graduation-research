package com.tabisketch.bean.form;

import java.util.UUID;

public class ExampleEditPlanForm {
    private ExampleEditPlanForm() {
    }

    public static EditPlanForm gen() {
        return new EditPlanForm(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                "title",
                "",
                true,
                false
        );
    }
}
