package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.DeparturePoint;
import com.tabisketch.bean.entity.DestinationPoint;
import com.tabisketch.bean.entity.Waypoint;
import com.tabisketch.bean.entity.WaypointList;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IDeparturePointsMapper;
import com.tabisketch.mapper.IDestinationPointsMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.mapper.IWaypointsMapper;
import com.tabisketch.service.IFindWaypointListAsEditWaypointListFormService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindWaypointListAsEditWaypointListFormService implements IFindWaypointListAsEditWaypointListFormService {
    private final IWaypointListsMapper waypointListsMapper;
    private final IDeparturePointsMapper departurePointsMapper;
    private final IWaypointsMapper waypointsMapper;
    private final IDestinationPointsMapper destinationPointsMapper;

    public FindWaypointListAsEditWaypointListFormService(
            final IWaypointListsMapper waypointListsMapper,
            final IDeparturePointsMapper departurePointsMapper,
            final IWaypointsMapper waypointsMapper,
            final IDestinationPointsMapper destinationPointsMapper
    ) {
        this.waypointListsMapper = waypointListsMapper;
        this.departurePointsMapper = departurePointsMapper;
        this.waypointsMapper = waypointsMapper;
        this.destinationPointsMapper = destinationPointsMapper;
    }

    @Override
    public EditWaypointListForm execute(UUID planUUID, int travelDay, String email) {
        final WaypointList waypointList = this.waypointListsMapper.selectByPlanUUIDAndTravelDayAndEmail(planUUID, travelDay, email);
        if (waypointList == null) throw new FailedSelectException("failed to find waypointList");

        final DeparturePoint departurePoint = this.departurePointsMapper.selectByWaypointListIdAndEmail(waypointList.getId(), email);
        if (departurePoint == null) throw new FailedSelectException("failed to find departurePoint");

        final List<Waypoint> waypoints = this.waypointsMapper.selectByWaypointListIdAndEmail(waypointList.getId(), email);
        if (waypoints == null) throw new FailedSelectException("failed to find waypoints");

        final DestinationPoint destinationPoint = this.destinationPointsMapper.selectByWaypointListIdAndEmail(waypointList.getId(), email);
        if (destinationPoint == null) throw new FailedSelectException("failed to find destinationPoint");

        return new EditWaypointListForm(waypointList, departurePoint, waypoints, destinationPoint);
    }
}
