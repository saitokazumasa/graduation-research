package com.tabisketch.bean.valueobject;

/// 経度
public class Longitude {
    public final double value;

    public Longitude(final double value) {
        this.value = value;
    }

    public double toRadians() {
        return Math.toRadians(value);
    }
}
