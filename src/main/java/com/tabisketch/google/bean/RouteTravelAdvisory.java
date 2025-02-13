package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 交通ゾーンの制限など、追加情報
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteTravelAdvisory {
    /// 通行料に関する情報
    private TollInfo tollInfo;
    /// 交通量の詳細を示す速度制限区間
    private SpeedReadingInterval speedReadingIntervals;
    /// 予測される燃料消費量（マイクロｌ）
    private String fuelConsumptionMicroliters;
    /// リクエストされた移動手段やルート修飾子に適さない制限が設定されている場合があるか
    private boolean routeRestrictionsPartiallyIgnored;
    /// 合計運賃または切符の合計金額
    private Money transitFare;
}
