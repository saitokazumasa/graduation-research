package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/// 到着地点
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DestinationPoint {
    /// 識別子
    private int id;
    /// 表示名
    private String label;
    /// Google Place Id
    private String placeId;
    /// 到着日時
    private LocalDateTime arrivalDatetime;
    /// 関連する「行先リスト」の識別子
    private int waypointListId;
}
