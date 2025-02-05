package com.tabisketch.optimize_route.bean.valueobject;

import com.tabisketch.optimize_route.constant.TransportationMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/// 2地点間の経路情報
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Route {
    /// 出発地点
    private DeparturePoint departurePoint;
    /// 到着地点
    private ArrivalPoint arrivalPoint;
    /// 交通手段
    private TransportationMode[] transportationModes;

    public Route(final DeparturePoint departurePoint, final ArrivalPoint arrivalPoint) {
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.transportationModes = null;
    }
}
