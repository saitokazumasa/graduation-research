package com.tabisketch.optimize_route.bean.valueobject;

import lombok.Getter;

import java.time.LocalDateTime;

/// 到着地点
@Getter
public class ArrivalPoint {
    /// 識別子
    private final int id;
    /// 地名
    private final String name;
    /// 住所
    private final String address;
    /// 到着日時
    private final LocalDateTime arrivalDateTime;
    /// 滞在時間（分）
    private final int stayTimeMinutes;

    public ArrivalPoint() {
        this.id = -1;
        this.name = null;
        this.address = null;
        this.arrivalDateTime = null;
        this.stayTimeMinutes = -1;
    }

    public ArrivalPoint(final int id, final String name, final String address, final int stayTimeMinutes) {
        assert id > 0;
        assert name != null && !name.isEmpty();
        assert address != null && !address.isEmpty();
        assert stayTimeMinutes >= 0;

        this.id = id;
        this.name = name;
        this.address = address;
        this.arrivalDateTime = null;
        this.stayTimeMinutes = stayTimeMinutes;
    }

    public LocalDateTime departureDateTime() {
        assert arrivalDateTime != null;
        return arrivalDateTime.plusMinutes(stayTimeMinutes);
    }
}
