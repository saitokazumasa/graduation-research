package com.tabisketch.optimize_route.bean.valueobject;

import com.tabisketch.optimize_route.constant.TransportationMode;

import lombok.Getter;

/// 2地点間の経路情報
@Getter
public class Route {
    /// 出発地点
    private final DeparturePoint departurePoint;
    /// 到着地点
    private final ArrivalPoint arrivalPoint;
    /// 移動時間
    private final int durationMinutes;
    /// 交通手段
    private final TransportationMode[] transportationModes;

    public Route() {
        this.departurePoint = new DeparturePoint();
        this.arrivalPoint = new ArrivalPoint();
        this.durationMinutes = -1;
        this.transportationModes = null;
    }

    public Route(final DeparturePoint departurePoint, final ArrivalPoint arrivalPoint) {
        assert departurePoint != null;
        assert arrivalPoint != null;

        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.durationMinutes = -1;
        this.transportationModes = null;
    }
}
