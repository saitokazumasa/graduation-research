package com.tabisketch.bean.valueobject;

/// 緯度
public class Latitude {
    public final double value;

    public Latitude(final double value) {
        this.value = value;
    }

    public double toRadians() {
        return Math.toRadians(value);
    }
}
