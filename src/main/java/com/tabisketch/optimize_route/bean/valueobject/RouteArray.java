package com.tabisketch.optimize_route.bean.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/// 経路情報リスト
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RouteArray {
    private Route[] routes;

    public RouteArray(final DeparturePoint departurePoint, final ArrivalPoint... arrivalPoints) {
        final var routes = new ArrayList<Route>();
        for (final var arrivalPoint : arrivalPoints) {
            final var route = new Route(departurePoint, arrivalPoint);
            routes.add(route);
        }
        this.routes = routes.toArray(new Route[0]);
    }
}
