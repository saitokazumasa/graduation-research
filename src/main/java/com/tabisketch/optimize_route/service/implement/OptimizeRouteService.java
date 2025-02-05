package com.tabisketch.optimize_route.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabisketch.optimize_route.bean.valueobject.*;
import com.tabisketch.optimize_route.service.ICallGeminiService;
import com.tabisketch.optimize_route.service.IOptimizeRouteService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class OptimizeRouteService implements IOptimizeRouteService {
    private final ICallGeminiService callGeminiService;
    private final ObjectMapper objectMapper;

    public OptimizeRouteService(final ICallGeminiService callGeminiService, final ObjectMapper objectMapper) {
        this.callGeminiService = callGeminiService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute() throws IOException {
        final var routeArray = new RouteArray(
                new DeparturePoint(1, "中野駅", "〒164-0001 東京都中野区中野５丁目３１−１", LocalDateTime.of(2025, 2, 5, 9, 0)),
                new ArrivalPoint(2, "浅草寺", "〒111-0032 東京都台東区浅草２丁目３−１", 30),
                new ArrivalPoint(3, "新宿駅", "〒160-0022 東京都新宿区新宿３丁目３８−１", 120),
                new ArrivalPoint(4, "東京工芸大学 杉並アニメーションミュージアム", "〒167-0043 東京都杉並区上荻３丁目２９−５ 杉並会館", 100)
        );

        final var systemInstruction = """
                ・routeごとにdeparturePoint、departureDateTimeからarrivalPointまで最も早く到着できる日時と移動手段を調べる
                ・departureDateTime,arrivalDateTimeは"yyyy-MM-ddTHH:mm:ss"の形式
                ・arrivalDateTimeは到着日時で書き換え
                ・transportationModesは移動手段で書き換え
                ・transportationModesはtransportationModeの配列
                ・transportationModeはenum[WALKING,BUS,TAXI,TRAIN]
                ・durationは何にも使わずそのまま返す
                ・https://www.navitime.co.jp/ https://www.google.co.jp/maps を使う
                ・回答はjsonのみで出力、それ以外の情報を含めない、改行を含めない
                """;

        final var json = this.objectMapper.writeValueAsString(routeArray);
        final var response = this.callGeminiService.execute(systemInstruction, json);
        final var replaced = response
                .replace("```", "")
                .replace("json", "");

        final var responseClass = this.objectMapper.readValue(replaced, RouteArray.class);

        // TODO: 最も早く着く組み合わせを選んで返す

        System.out.println(responseClass);
    }
}
