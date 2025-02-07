package com.tabisketch.bean.valueobject;

/// 十進法度単位の緯度経度
///
/// @param latitude  緯度
/// @param longitude 経度
public record LatLng(double latitude, double longitude) {
    public LatLng {
        assert latitude >= -90 && latitude <= 90;
        assert longitude >= -180 && longitude <= 180;
    }
}
