package com.tabisketch.bean.view_model;

import com.tabisketch.bean.entity.WaypointList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaypointListViewModel {
    private int id;
    private int travelDay;

    public WaypointListViewModel(final WaypointList waypointList) {
        this.id = waypointList.getId();
        this.travelDay = waypointList.getTravelDay();
    }
}
