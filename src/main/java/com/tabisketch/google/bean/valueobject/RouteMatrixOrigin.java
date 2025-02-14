package com.tabisketch.google.bean.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 出発地点
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteMatrixOrigin {
    private Waypoint waypoint;
    private RouteModifiers routeModifiers;
}
