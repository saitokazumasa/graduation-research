package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/// 出発地点
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeparturePoint {
    /// 識別子
    private int id;
    /// Google Place Id
    private String placeId;
    /// 出発日時
    private LocalDateTime departureDatetime;
    /// 関連する「行先リスト」の識別子
    private int waypointListId;
}
