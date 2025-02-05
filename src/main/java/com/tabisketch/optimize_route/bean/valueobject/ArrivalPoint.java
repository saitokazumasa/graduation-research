package com.tabisketch.optimize_route.bean.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/// 到着地点
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArrivalPoint {
    /// 識別子
    private int id;
    /// 地名
    private String name;
    /// 住所
    private String address;
    /// 到着日時
    private LocalDateTime arrivalDateTime;
    /// 滞在時間
    private int duration;

    public ArrivalPoint(final int id, final String name, final String address, final int duration) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.arrivalDateTime = null;
        this.duration = duration;
    }
}
