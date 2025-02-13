package com.tabisketch.google.constant;

/// 交通機関のルートのルーティング設定
public enum TransitRoutingPreference {
    /// 未指定
    TRANSIT_ROUTING_PREFERENCE_UNSPECIFIED,
    /// 歩行距離に制限を付けてルートを計算
    LESS_WALKING,
    /// 乗り換え回数に制限を付けてルートを計算
    FEWER_TRANSFERS
}
