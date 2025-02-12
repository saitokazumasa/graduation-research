package com.tabisketch.bean.entity;

public class ExampleWaypointList {
    private ExampleWaypointList() {
    }

    public static WaypointList gen() {
        return new WaypointList(
                1,
                1,
                1
        );
    }
}
