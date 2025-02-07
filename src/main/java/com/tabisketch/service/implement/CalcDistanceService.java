package com.tabisketch.service.implement;

import com.tabisketch.bean.valueobject.Distance;
import com.tabisketch.bean.valueobject.LatLng;
import com.tabisketch.service.ICalcDistanceService;
import net.sf.geographiclib.Geodesic;
import org.springframework.stereotype.Service;

@Service
public class CalcDistanceService implements ICalcDistanceService {
    @Override
    public Distance execute(final LatLng start, final LatLng end) {
        final var geodesic = Geodesic.WGS84;
        final var data = geodesic.Inverse(start.latitude(), start.longitude(), end.latitude(), end.longitude());
        return new Distance(data.s12);
    }
}
