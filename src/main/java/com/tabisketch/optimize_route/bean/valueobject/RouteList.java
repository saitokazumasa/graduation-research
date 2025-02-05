package com.tabisketch.optimize_route.bean.valueobject;

import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.IntStream;

/// 経路情報リスト
@Getter
public class RouteList {
    private final ArrayList<Route> routes;

    public RouteList() {
        routes = new ArrayList<>();
    }

    public RouteList(final ArrayList<Route> routes) {
        assert routes != null;
        this.routes = routes;
    }

    public RouteList(final DeparturePoint departurePoint, final ArrivalPoint... arrivalPoints) {
        assert departurePoint != null;
        assert arrivalPoints != null && arrivalPoints.length > 0;

        final var routes = new ArrayList<Route>();
        for (final var arrivalPoint : arrivalPoints) {
            final var route = new Route(departurePoint, arrivalPoint);
            routes.add(route);
        }
        this.routes = routes;
    }

    public Route getLast() {
        return routes.getLast();
    }

    public RouteList add(final Route route) {
        assert routes != null;
        assert route != null;

        final var list = new ArrayList<>(this.routes);
        list.add(route);
        return new RouteList(list);
    }

    public RouteList remove(final Route route) {
        assert routes != null && !routes.isEmpty();
        assert route != null;

        final var list = new ArrayList<>(this.routes);
        final int index = findIndex(route);
        list.remove(index);
        return new RouteList(list);
    }

    public int findIndex(final Route route) {
        assert routes != null && !routes.isEmpty();
        assert route != null;

        final var index = IntStream.range(0, this.routes.size())
                .filter(i -> {
                    final var isMatchDeparturePoint = routes.get(i).getDeparturePoint().getId() == route.getDeparturePoint().getId();
                    final var isMatchArrivalPoint = routes.get(i).getArrivalPoint().getId() == route.getArrivalPoint().getId();
                    return isMatchDeparturePoint && isMatchArrivalPoint;
                })
                .findFirst();
        if (index.isEmpty()) throw new NullPointerException("route not found");

        return index.getAsInt();
    }

    public Route findFastestRoute() {
        assert routes != null && !routes.isEmpty();

        var fastestRoute = routes.getFirst();
        for (final var route : routes) {
            if (fastestRoute.getDurationMinutes() <= route.getDurationMinutes()) continue;
            fastestRoute = route;
        }
        return fastestRoute;
    }

    public boolean isEmpty() {
        assert routes != null;
        return routes.isEmpty();
    }
}
