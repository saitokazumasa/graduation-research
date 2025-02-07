package com.tabisketch.service;

import com.tabisketch.bean.valueobject.EstimatedTravelDistance;
import com.tabisketch.bean.valueobject.StraightLineDistance;

/// 直線距離から移動距離を概算する
public interface ICalcEstimatedTravelDistanceService {
    EstimatedTravelDistance execute(final StraightLineDistance straightLineDistance);
}
