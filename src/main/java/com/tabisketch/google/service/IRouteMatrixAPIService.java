package com.tabisketch.google.service;

import com.tabisketch.google.bean.entity.RouteMatrixAPIRequest;
import com.tabisketch.google.bean.entity.RouteMatrixAPIResponse;

import java.io.IOException;
import java.util.List;

public interface IRouteMatrixAPIService {
    List<RouteMatrixAPIResponse> execute(final RouteMatrixAPIRequest routeMatrixAPIRequest) throws IOException;
}
