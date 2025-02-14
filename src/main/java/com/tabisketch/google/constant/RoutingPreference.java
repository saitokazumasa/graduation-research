package com.tabisketch.google.constant;

/// ルートの計算時に考慮すべき要素
public enum RoutingPreference {
    /// 未指定
    ROUTING_PREFERENCE_UNSPECIFIED,
    /// リアルタイムの交通状況を考慮せずにルートを計算
    TRAFFIC_UNAWARE,
    /// リアルタイムの交通状況を考慮してルートを計算
    TRAFFIC_AWARE,
    /// 現在の交通状況を考慮してルートを計算
    TRAFFIC_AWARE_OPTIMAL
}
