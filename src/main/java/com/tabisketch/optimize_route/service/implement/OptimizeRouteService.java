package com.tabisketch.optimize_route.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.optimize_route.bean.valueobject.*;
import com.tabisketch.optimize_route.service.ICallGeminiService;
import com.tabisketch.optimize_route.service.IOptimizeRouteService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OptimizeRouteService implements IOptimizeRouteService {
    private final ICallGeminiService callGeminiService;
    private final ObjectMapper objectMapper;

    public OptimizeRouteService(final ICallGeminiService callGeminiService, final ObjectMapper objectMapper) {
        this.callGeminiService = callGeminiService;
        this.objectMapper = objectMapper;
    }

    @Override
    public RouteList execute(final RouteList routeList, final EndPoint endPoint) throws IOException {
        final var empty = new RouteList();
        final var fastestRouteList = searchFastestRouteList(routeList, empty);

        // 最終ポイントまでのルートを追加
        final var lastArrivalPoint = fastestRouteList.getLast().getArrivalPoint();
        final var lastDeparturePoint = generateDeparturePointByArrivalPoint(lastArrivalPoint);
        final var endArrivalPoint = endPoint.toArrivalPoint();
        final var endRoute = new Route(lastDeparturePoint, endArrivalPoint);
        final var fastestEndRoute = searchFastestRoute(endRoute);

        return fastestRouteList.add(fastestEndRoute);
    }

    /// 最も早く周れるルートのリストを探す
    private RouteList searchFastestRouteList(final RouteList routeList, final RouteList fastestRouteList) throws IOException {
        final var fastestRoute = searchFastestRoute(routeList);
        final var newFastestRouteList = fastestRouteList.add(fastestRoute);
        final var remainingRouteList = routeList.remove(fastestRoute);

        if (remainingRouteList.isEmpty()) return newFastestRouteList;

        final var nextDeparturePoint = generateDeparturePointByArrivalPoint(fastestRoute.getArrivalPoint());
        final var nextRouteList = generateRouteListByRemainingRouteList(nextDeparturePoint, remainingRouteList);
        return searchFastestRouteList(nextRouteList, newFastestRouteList);
    }

    /// 最も早く着く到着ポイントへのルートを探す
    private Route searchFastestRoute(final RouteList routeList) throws IOException {
        final var systemInstruction = """
                ・routeごとにdepartureDateTimeにdeparturePointを出発し、arrivalPointまで、最も早く到着できる日時と移動手段を調べる
                ・departureDateTime,arrivalDateTimeは"yyyy-MM-ddTHH:mm:ss"の形式
                ・durationMinutesは移動時間（分）の数値のみで置き換え
                ・arrivalDateTimeは到着日時で書き換え
                ・transportationModesは移動手段で書き換え
                ・transportationModesはtransportationModeの配列
                ・transportationModeはenum[WALKING,BUS,TAXI,TRAIN]
                ・stayTimeMinutesは何にも使わずそのまま返す
                ・https://www.navitime.co.jp/ を使う
                ・回答はjsonのみで出力、それ以外の情報を含めない
                """;
        final var json = this.objectMapper.writeValueAsString(routeList);
        final var response = this.callGeminiService.execute(systemInstruction, json);
        System.out.println(response);
        final var replaced = response
                .replace("```", "")
                .replace("json", "");
        final var responseClass = this.objectMapper.readValue(replaced, RouteList.class);
        return responseClass.findFastestRoute();
    }

    /// 最も早く着く到着ポイントへのルートを探す
    private Route searchFastestRoute(final Route route) throws IOException {
        final var systemInstruction = """
                ・departurePoint、departureDateTimeからarrivalPointまで最も早く到着できる日時と移動手段を調べる
                ・departureDateTime,arrivalDateTimeは"yyyy-MM-ddTHH:mm:ss"の形式
                ・durationMinutesは移動時間（分）の数値のみで置き換え
                ・arrivalDateTimeは到着日時で書き換え
                ・transportationModesは移動手段で書き換え
                ・transportationModesはtransportationModeの配列
                ・transportationModeはenum[WALKING,BUS,TAXI,TRAIN]
                ・stayTimeMinutesは何にも使わずそのまま返す
                ・https://www.navitime.co.jp/ https://www.google.co.jp/maps を使う
                ・回答はjsonのみで出力、それ以外の情報を含めない、改行を含めない
                """;
        final var json = this.objectMapper.writeValueAsString(route);
        final var response = this.callGeminiService.execute(systemInstruction, json);
        final var replaced = response
                .replace("```", "")
                .replace("json", "");
        return this.objectMapper.readValue(replaced, Route.class);
    }

    /// 到着ポイントから出発ポイントを生成する
    private DeparturePoint generateDeparturePointByArrivalPoint(final ArrivalPoint arrivalPoint) {
        return new DeparturePoint(
                arrivalPoint.getId(),
                arrivalPoint.getName(),
                arrivalPoint.getAddress(),
                arrivalPoint.departureDateTime()
        );
    }

    /// 残りのルートから次のルートリストを生成する
    private RouteList generateRouteListByRemainingRouteList(final DeparturePoint departurePoint, final RouteList remainingRouteList) {
        var routeList = new RouteList();
        for (final var remainingRoute : remainingRouteList.getRoutes()) {
            final var route = new Route(departurePoint, remainingRoute.getArrivalPoint());
            routeList = routeList.add(route);
        }
        return routeList;
    }
}
