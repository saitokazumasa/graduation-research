package com.tabisketch.google.constant;

/// 所要時間を計算する上での仮定条件
public enum TrafficModel {
    /// 未指定
    TRAFFIC_MODEL_UNSPECIFIED,
    /// 過去と現在の交通状況のデータを基に見積もった最適な移動時間
    BEST_GUESS,
    /// 実際の移動距離よりも大きい値
    PESSIMISTIC,
    /// 実際の移動距離よりも小さい値
    OPTIMISTIC
}
