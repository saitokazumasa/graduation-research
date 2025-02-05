package com.tabisketch.optimize_route.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.optimize_route.bean.valueobject.ArrivalPoint;
import com.tabisketch.optimize_route.bean.valueobject.DeparturePoint;
import com.tabisketch.optimize_route.bean.valueobject.EndPoint;
import com.tabisketch.optimize_route.bean.valueobject.RouteList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
public class OptimizeRouteServiceTest {
    @Autowired
    private IOptimizeRouteService optimizeRouteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testExecute() throws IOException {
        final var routeList = new RouteList(
                new DeparturePoint(1, "中野駅", "東京都中野区中野５丁目３１−１", LocalDateTime.of(2025, 2, 5, 9, 0)),
                new ArrivalPoint(2, "浅草寺", "東京都台東区浅草２丁目３−１", 30),
                new ArrivalPoint(3, "新宿駅", "東京都新宿区新宿３丁目３８−１", 120),
                new ArrivalPoint(4, "東京工芸大学 杉並アニメーションミュージアム", "東京都杉並区上荻３丁目２９−５ 杉並会館", 100)
        );
        final var endPoint = new EndPoint(5, "東京駅", "東京都千代田区丸の内１丁目");

//        System.out.println(objectMapper.writeValueAsString(routeList));
        final var optimizedRouteList = this.optimizeRouteService.execute(routeList, endPoint);

        final var output = new StringBuilder();
        for (final var route : optimizedRouteList.getRoutes()) {
            final var departurePoint = route.getDeparturePoint();
            final var arrivalPoint = route.getArrivalPoint();
            output.append("出発地点 | ");
            output.append("ID:").append(departurePoint.getId()).append(" ");
            output.append("地名:").append(departurePoint.getName()).append(" ");
            output.append("住所:").append(departurePoint.getAddress()).append("\n");
            output.append("出発日時 | ").append(departurePoint.getDepartureDateTime()).append("\n");
            output.append("到着地点 | ");
            output.append("ID:").append(arrivalPoint.getId()).append(" ");
            output.append("地名:").append(arrivalPoint.getName()).append(" ");
            output.append("住所:").append(arrivalPoint.getAddress()).append("\n");
            output.append("到着日時 | ").append(arrivalPoint.getArrivalDateTime()).append("\n");
            output.append("滞在時間 | ").append(arrivalPoint.getStayTimeMinutes()).append("\n");
            output.append("移動時間 | ").append(route.getDurationMinutes()).append("分\n");
            output.append("移動手段 | ").append(this.objectMapper.writeValueAsString(route.getTransportationModes())).append("\n");
            output.append("\n");
        }
        System.out.println(output);
    }
}
