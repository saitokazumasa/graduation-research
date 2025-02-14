package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;
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
    /// Google Place Id
    private String placeId;
    /// 到着日時
    private LocalDateTime arrivalDatetime;
    /// ここに着くまでの移動手段
    private Transporation transporation;
    /// ここに着くまでにかかる移動時間（秒）
    private int duration;
    /// 関連する「行先リスト」の識別子
    private int waypointListId;
}
