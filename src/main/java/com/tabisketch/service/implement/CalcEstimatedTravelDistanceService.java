package com.tabisketch.service.implement;

import com.tabisketch.bean.valueobject.EstimatedTravelDistance;
import com.tabisketch.bean.valueobject.StraightLineDistance;
import com.tabisketch.service.ICalcEstimatedTravelDistanceService;
import org.springframework.stereotype.Service;

@Service
public class CalcEstimatedTravelDistanceService implements ICalcEstimatedTravelDistanceService {
    @Override
    public EstimatedTravelDistance execute(final StraightLineDistance straightLineDistance) {
        // 参考資料より、直線距離 * 1/0.776 = 道のりの概算
        // 参考資料: https://ccmath.meijo-u.ac.jp/~suzukin/dl/%E9%81%93%E8%B7%AF%E8%B7%9D%E9%9B%A2(%E8%A7%A3%E8%AA%AC)_1019.pdf
        final double value = straightLineDistance.value() * 1.2886597938144329;
        return new EstimatedTravelDistance(value);
    }
}
