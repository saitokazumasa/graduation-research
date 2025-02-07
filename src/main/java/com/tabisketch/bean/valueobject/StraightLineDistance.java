package com.tabisketch.bean.valueobject;

/// ｍで表した２地点間の直線距離
public record StraightLineDistance(double value) {
    public StraightLineDistance {
        assert value >= 0;
    }
}
