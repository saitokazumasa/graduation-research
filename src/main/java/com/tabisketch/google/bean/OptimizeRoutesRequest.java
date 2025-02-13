package com.tabisketch.google.bean;

import com.tabisketch.google.constant.RouteTravelMode;
import com.tabisketch.google.constant.TrafficModel;
import com.tabisketch.google.constant.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptimizeRoutesRequest {
    /// 出発地点の配列
    private RouteMatrixOrigin[] origins;
    /// 目的地の配列
    private RouteMatrixDestination[] destinations;
    /// 移動手段
    private RouteTravelMode travelMode;
    /// 出発日時
    private LocalDateTime departureTime;
    /// 測定単位
    private Units units;
    /// 所要時間を計算する上での仮定条件
    private TrafficModel trafficModel;
    /// TRANSITオプション
    private TransitPreferences transitPreferences;
}
