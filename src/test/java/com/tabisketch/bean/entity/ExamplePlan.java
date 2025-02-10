package com.tabisketch.bean.entity;

import java.util.UUID;

public class ExamplePlan {
    private ExamplePlan() {
    }

    public static Plan gen() {
        return new Plan(
                1,
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82"),
                "title",
                true,
                false,
                1
        );
    }
}
