package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;

public class ExampleWaypointList {
    private ExampleWaypointList() {
    }

    public static WaypointList gen() {
        return new WaypointList(
                1,
                1,
                Transporation.WALK,
                1
        );
    }
}
