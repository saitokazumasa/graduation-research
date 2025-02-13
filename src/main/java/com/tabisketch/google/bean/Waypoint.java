package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Waypoint {
    /// 停止せずに通過する場合はtrueにする
    private boolean via;
    /// 車両が乗車または降車のために停車することを目的としたウェイポイントであることを示す
    private boolean vehicleStopover;
    /// 車両を優先的に道路の特定の側に停車することを指示する
    private boolean sideOfRoad;
    /// Google Place ID
    private String placeId;
}
