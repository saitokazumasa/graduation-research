package com.tabisketch.google.bean;

import com.tabisketch.google.constant.TollPass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// ルート計算オプション
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteModifiers {
    /// 有料道路を含まない
    private boolean avoidTolls;
    /// 高速道路を含まない
    private boolean avoidHighways;
    /// フェリーを含まない
    private boolean avoidFerries;
    /// 屋内を回避
    private boolean avoidIndoor;
    /// 車両情報
    private VehicleInfo vehicleInfo;
    /// 通行料金パス
    private TollPass[] tollPasses;
}
