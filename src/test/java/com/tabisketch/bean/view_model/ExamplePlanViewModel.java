package com.tabisketch.bean.view_model;

import java.util.UUID;

public class ExamplePlanViewModel {
    private ExamplePlanViewModel() {
    }

    public static PlanViewModel gen() {
        return new PlanViewModel(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                "title",
                "",
                true,
                false
        );
    }
}
