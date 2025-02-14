package com.tabisketch.google.constant;

/// 交通機関の種類
public enum TransitTravelMode {
    /// 未指定
    TRANSIT_TRAVEL_MODE_UNSPECIFIED,
    /// バス
    BUS,
    /// 地下鉄
    SUBWAY,
    /// 電車
    TRAIN,
    /// ライトレールや路面電車
    LIGHT_RAIL,
    /// 鉄道 SUBWAY、TRAIN、LIGHT_RAIL の組み合わせと同じ
    RAIL
}
