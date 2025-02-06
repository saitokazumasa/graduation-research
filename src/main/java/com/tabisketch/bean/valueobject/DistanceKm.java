package com.tabisketch.bean.valueobject;

import static java.lang.Math.atan;
import static java.lang.Math.acos;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.pow;

/// 測地線航海法を用いて、２地点間の距離（ｋｍ）を求める
public class DistanceKm {
    public final double value;

    public DistanceKm(final Point pointA, final Point pointB) {
        assert pointA != null;
        assert pointB != null;

        final double RX = 6378.137;
        final double RY = 6356.752;
        final double x1 = pointA.latitude.toRadians();
        final double y1 = pointA.longitude.toRadians();
        final double x2 = pointB.latitude.toRadians();
        final double y2 = pointB.longitude.toRadians();

        // 参考: 測地線航海法
        final double p1 = atan(RY / RX * tan(y1));
        final double p2 = atan(RY / RX * tan(y2));
        final double X = acos(sin(p1) * sin(p2) + cos(p1) * cos(p2) * cos(x1 - x2));
        final double F = (RX - RY) / RX;
        final double dr = F / 8 * ((sin(X) - X) * pow((sin(p1) + sin(p2)), 2.0) / pow(cos(X / 2), 2.0) - (sin(X) + X) * pow((sin(p1) - sin(p2)), 2.0) / pow(sin(X / 2), 2.0));
        this.value = RX * (X + dr);
    }
}
