package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/// 経由地点
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Waypoint {
    /// 識別子
    private int id;
    /// 表示名
    private String label;
    /// Google Place Id
    private String placeId;
    /// 順番
    private int visitOrder;
    /// 希望到着日時
    private LocalDateTime preferredArrivalDatetime;
    /// 到着日時
    private LocalDateTime arrivalDatetime;
    /// 滞在時間（秒）
    private int stayTime;
    /// 次の地点までの移動手段
    private Transporation transporation;
    /// 次の地点までにかかる移動時間（秒）
    private int duration;
    /// 予算
    private int budget;
    /// 関連する「行先リスト」の識別子
    private int waypointListId;
}
