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
                LocalDateTime.now(),
                1
        );
    }
}
