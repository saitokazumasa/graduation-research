package com.tabisketch.google.bean.valueobject;

import com.tabisketch.google.constant.TransitRoutingPreference;
import com.tabisketch.google.constant.TransitTravelMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// TRANSITベースのルート設定
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransitPreferences {
    /// 使用する一連の移動手段
    private TransitTravelMode[] allowedTravelModes;
    /// 交通機関のルートのルーティング設定
    private TransitRoutingPreference routingPreference;
}
