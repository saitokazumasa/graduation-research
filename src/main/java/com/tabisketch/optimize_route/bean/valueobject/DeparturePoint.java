package com.tabisketch.optimize_route.bean.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/// 出発地点
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeparturePoint {
    /// 識別子
    private int id;
    /// 地名
    private String name;
    /// 住所
    private String address;
    /// 出発日時
    private LocalDateTime departureDateTime;
}
