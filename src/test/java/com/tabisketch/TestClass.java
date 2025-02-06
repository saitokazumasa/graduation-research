package com.tabisketch;

import com.tabisketch.bean.valueobject.DistanceKm;
import com.tabisketch.bean.valueobject.Latitude;
import com.tabisketch.bean.valueobject.Longitude;
import com.tabisketch.bean.valueobject.Point;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestClass {

    @Test
    public void test() {
        // 東京駅
        final var latitude1 = new Latitude(35.68146508229269);
        final var longitude1 = new Longitude(139.7670550473845);
        final var point1 = new Point(latitude1, longitude1);

        // 新宿駅
        final var latitude2 = new Latitude(35.68968509705526);
        final var longitude2 = new Longitude(139.7005927539302);
        final var point2 = new Point(latitude2, longitude2);

        final var distance = new DistanceKm(point1  , point2);
        System.out.println(distance.value);
    }
}
