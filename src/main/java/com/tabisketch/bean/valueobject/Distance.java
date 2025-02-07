package com.tabisketch.bean.valueobject;

/// ｍで表した２地点間の距離
public record Distance(double value) {
    public Distance {
        assert value >= 0;
    }
}
