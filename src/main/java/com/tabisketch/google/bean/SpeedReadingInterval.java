package com.tabisketch.google.bean;

import com.tabisketch.google.constant.Speed;

/// 交通量インジケーター
public class SpeedReadingInterval {
    /// ポリライン内の開始インデックス
    private Integer startPolylinePointIndex;
    /// ポリライン内の終了インデックス
    private Integer endPolylinePointIndex;
    /// 交通速度
    private Speed speed;
}
