package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;

import java.time.LocalDateTime;

public class ExampleWaypoint {
    public static Waypoint gen() {
        return new Waypoint(
                1,
                "label",
                "placeId",
                1,
                null,
                LocalDateTime.of(2025, 1, 1, 0, 0, 0),
                0,
                Transporation.WALK,
                0,
                0,
                1
        );
    }
}
