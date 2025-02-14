package com.tabisketch.bean.entity;

import com.tabisketch.constant.Transporation;
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
    /// 表示名
    private String label;
    /// Google Place Id
    private String placeId;
    /// 出発日時
    private LocalDateTime departureDatetime;
    /// 次の地点までの移動手段
    private Transporation transporation;
    /// 次の地点までにかかる移動時間（秒）
    private int duration;
    /// 関連する「行先リスト」の識別子
    private int waypointListId;
}
