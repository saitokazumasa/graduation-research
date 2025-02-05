package com.tabisketch.optimize_route.bean.valueobject;

import lombok.Getter;

import java.time.LocalDateTime;

/// 終了地点
@Getter
public class EndPoint {
    /// 識別子
    private final int id;
    /// 地名
    private final String name;
    /// 住所
    private final String address;
    /// 到着日時
    private final LocalDateTime arrivalDateTime;

    public EndPoint() {
        this.id = -1;
        this.name = null;
        this.address = null;
        this.arrivalDateTime = null;
    }

    public EndPoint(final int id, final String name, final String address) {
        assert id > 0;
        assert name != null && !name.isEmpty();
        assert address != null && !address.isEmpty();

        this.id = id;
        this.name = name;
        this.address = address;
        this.arrivalDateTime = null;
    }

    public ArrivalPoint toArrivalPoint() {
        return new ArrivalPoint(
                this.id,
                this.name,
                this.address,
                0
        );
    }
}
