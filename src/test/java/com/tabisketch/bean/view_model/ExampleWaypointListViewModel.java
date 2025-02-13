package com.tabisketch.bean.view_model;

public class ExampleWaypointListViewModel {
    private ExampleWaypointListViewModel() {}

    public static WaypointListViewModel gen() {
        return new WaypointListViewModel(
                2,
                2
        );
    }
}
