package com.tabisketch.google.bean.entity;

import com.tabisketch.google.bean.valueobject.Status;
import com.tabisketch.google.constant.RouteMatrixElementCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteMatrixAPIResponse {
    /// 出発地点のインデックス
    private Integer originIndex;
    /// 目的地のインデックス
    private Integer destinationIndex;
    /// エラーステータスコード
    private Status status;
    /// 移動距離（ｍ）
    private Integer distanceMeters;
    /// 所要時間（ｓ）
    private String duration;
    /// ルートが見つかったかどうか
    private RouteMatrixElementCondition condition;
}
