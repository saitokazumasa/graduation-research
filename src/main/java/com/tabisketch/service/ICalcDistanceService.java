package com.tabisketch.service;

import com.tabisketch.bean.valueobject.Distance;
import com.tabisketch.bean.valueobject.LatLng;

/// ２地点の緯度経度から距離を計算する
public interface ICalcDistanceService {
    Distance execute(final LatLng start, final LatLng end);
}
