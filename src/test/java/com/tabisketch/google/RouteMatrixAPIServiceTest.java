package com.tabisketch.google;

import com.tabisketch.google.bean.entity.RouteMatrixAPIRequest;
import com.tabisketch.google.constant.RouteTravelMode;
import com.tabisketch.google.constant.TransitTravelMode;
import com.tabisketch.google.service.implement.RouteMatrixAPIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
public class RouteMatrixAPIServiceTest {
    @Autowired
    private RouteMatrixAPIService routeMatrixAPIService;

    @Test
    public void testExecute() throws IOException {
        final var request = RouteMatrixAPIRequest.builder()
                // 中野駅
                .addOrigin("ChIJcTUvs5byGGARTgc0rPIEgVY", false, false, false, false)
                // 浅草寺
                .addDestination("ChIJ8T1GpMGOGGARDYGSgpooDWw")
                // 東京タワー
                .addDestination("ChIJCewJkL2LGGAR3Qmk0vCTGkg")
                // 練馬区立美術館
                .addDestination("ChIJRwH1ILHtGGARbxZaKQyO3HQ")
                .setTravelMode(RouteTravelMode.WALK)
                .setDepartureTime(LocalDateTime.now())
                .setTransitPreferences(TransitTravelMode.TRANSIT_TRAVEL_MODE_UNSPECIFIED)
                .build();

        // NOTE: RouteMatrixAPIテストをしたい場合は、以下のコメントアウトを解除して実行する
//        final var response = this.routeMatrixAPIService.execute(request);
//        System.out.println(response);
    }
}
