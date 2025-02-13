package com.tabisketch.google.constant;

/// レスポンスに使用されるルーティング モード
public enum FallbackRoutingMode {
    /// 未指定
    FALLBACK_ROUTING_MODE_UNSPECIFIED,
    /// レスポンスの計算に TRAFFIC_UNAWARE RoutingPreference が使用された
    FALLBACK_TRAFFIC_UNAWARE,
    /// レスポンスの計算に TRAFFIC_AWARE RoutingPreference が使用された
    FALLBACK_TRAFFIC_AWARE,
}
