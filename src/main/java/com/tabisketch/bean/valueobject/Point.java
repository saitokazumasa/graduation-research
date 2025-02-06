package com.tabisketch.bean.valueobject;

/// 地点
public class Point {
    public final Latitude latitude;
    public final Longitude longitude;

    public Point(final Latitude latitude, final Longitude longitude) {
        assert latitude != null;
        assert longitude != null;

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
