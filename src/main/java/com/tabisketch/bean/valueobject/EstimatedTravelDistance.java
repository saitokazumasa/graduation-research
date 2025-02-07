package com.tabisketch.bean.valueobject;

/// ｍで表した２地点間の概算の移動距離
public record EstimatedTravelDistance(double value) {
    public EstimatedTravelDistance {
        assert value >= 0;
    }
}
