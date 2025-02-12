package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 行き先リスト
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WaypointList {
    /// 識別子
    private int id;
    /// 日にち
    private int travelDay;
    /// 関連する「旅行プラン」の識別子
    private int planId;
}
