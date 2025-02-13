package com.tabisketch.google.bean;

import com.tabisketch.google.constant.RouteMatrixElementCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptimizeRoutesResponse {
    /// エラーステータスコード
    private Status status;
    /// ルートが見つかったかどうか
    private RouteMatrixElementCondition condition;
    /// 移動距離（ｍ）
    private Integer distanceMeters;
    /// 所要時間（ｓ）
    private String duration;
    /// 交通状況を考慮しない所要時間（ｓ）
    private String staticDuration;
    /// ルートに関する追加情報
    private RouteTravelAdvisory travelAdvisory;
    /// フォールバックレスポンスに関する詳細情報
    private FallbackInfo fallbackInfo;
    /// RouteMatrixElementのプロパティのテキスト表現
    private LocalizedValues localizedValues;
    /// 出発地点のインデックス
    private Integer originIndex;
    /// 目的地のインデックス
    private Integer destinationIndex;
}
