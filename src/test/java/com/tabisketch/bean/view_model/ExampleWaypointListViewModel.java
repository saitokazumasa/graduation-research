package com.tabisketch.bean.view_model;

import com.tabisketch.constant.Transporation;

public class ExampleWaypointListViewModel {
    private ExampleWaypointListViewModel() {
    }

    public static WaypointListViewModel gen() {
        return new WaypointListViewModel(
                2,
                Transporation.WALK,
                2
        );
    }
}
