package com.tabisketch.google.constant;

/// ポリライン速度
public enum Speed {
    /// 未指定
    SPEED_UNSPECIFIED,
    /// 通常の速度
    NORMAL,
    /// 速度低下は検出されたが、渋滞は発生していない
    SLOW,
    /// 渋滞を検出
    TRAFFIC_JAM
}
