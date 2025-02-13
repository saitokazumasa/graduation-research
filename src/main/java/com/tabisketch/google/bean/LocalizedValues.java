package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// 特定のプロパティのテキスト
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocalizedValues {
    /// 移動距離
    private LocalizedText distance;
    /// 交通状況を考慮した所要時間
    private LocalizedText duration;
    /// 交通状況を考慮しない所要時間
    private LocalizedText staticDuration;
    /// 交通機関の運賃
    private LocalizedText transitFare;
}
