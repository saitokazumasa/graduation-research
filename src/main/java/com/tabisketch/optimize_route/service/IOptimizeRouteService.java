package com.tabisketch.optimize_route.service;

import com.tabisketch.optimize_route.bean.valueobject.EndPoint;
import com.tabisketch.optimize_route.bean.valueobject.RouteList;

import java.io.IOException;

public interface IOptimizeRouteService {
    RouteList execute(final RouteList routeList, final EndPoint endPoint) throws IOException;
}
