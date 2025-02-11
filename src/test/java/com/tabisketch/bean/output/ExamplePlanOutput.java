package com.tabisketch.bean.output;

import java.util.UUID;

public class ExamplePlanOutput {
    private ExamplePlanOutput() {
    }

    public static PlanOutput gen() {
        return new PlanOutput(
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                "title",
                "",
                true,
                false
        );
    }
}
