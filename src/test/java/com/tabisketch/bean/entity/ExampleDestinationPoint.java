package com.tabisketch.bean.entity;

import java.time.LocalDateTime;

public class ExampleDestinationPoint {
    private ExampleDestinationPoint() {
    }

    public static DestinationPoint gen() {
        return new DestinationPoint(
                1,
                "label",
                "placeId",
                LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                1
        );
    }
}
