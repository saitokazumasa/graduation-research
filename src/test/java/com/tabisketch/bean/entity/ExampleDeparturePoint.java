package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;

import java.time.LocalDateTime;

public class ExampleDeparturePoint {
    private ExampleDeparturePoint() {
    }

    public static DeparturePoint gen() {
        return new DeparturePoint(
                1,
                "label",
                "placeId",
                LocalDateTime.now(),
                Transporation.WALK,
                0,
                1
        );
    }
}
