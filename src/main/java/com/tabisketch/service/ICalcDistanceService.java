package com.tabisketch.service;

import com.tabisketch.bean.valueobject.StraightLineDistance;
import com.tabisketch.bean.valueobject.LatLng;

/// ２地点の緯度経度から直線距離を計算する
public interface ICalcDistanceService {
    StraightLineDistance execute(final LatLng start, final LatLng end);
}
