package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.*;
import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.constant.Transporation;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.*;
import com.tabisketch.service.ICreateWaypointListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateWaypointListService implements ICreateWaypointListService {
    private final IPlansMapper plansMapper;
    private final IWaypointListsMapper waypointListsMapper;
    private final IDeparturePointsMapper departurePointsMapper;
    private final IWaypointsMapper waypointsMapper;
    private final IDestinationPointsMapper destinationPointsMapper;

    public CreateWaypointListService(
            final IPlansMapper plansMapper,
            final IWaypointListsMapper waypointListsMapper,
            final IDeparturePointsMapper departurePointsMapper,
            final IWaypointsMapper waypointsMapper,
            final IDestinationPointsMapper destinationPointsMapper
    ) {
        this.plansMapper = plansMapper;
        this.waypointListsMapper = waypointListsMapper;
        this.departurePointsMapper = departurePointsMapper;
        this.waypointsMapper = waypointsMapper;
        this.destinationPointsMapper = destinationPointsMapper;
    }

    @Override
    @Transactional
    public EditWaypointListForm execute(final String email, final CreateWaypointListForm form) {
        // プラン取得
        final Plan plan = this.plansMapper.selectByUUIDAndEmail(form.getPlanUUID(), email);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // 作成
        final var waypointList = new WaypointList(-1, form.getTravelDay(), Transporation.WALK, plan.getId());
        final boolean wasInsertWaypointList = this.waypointListsMapper.insert(waypointList) == 1;
        if (!wasInsertWaypointList) throw new FailedInsertException("failed to insert waypointList");

        final var cdepaForm = form.getDeparturePoint();
        final var departurePoint = new DeparturePoint(-1, cdepaForm.getLabel(), cdepaForm.getPlaceId(), cdepaForm.getDepartureDatetime(), cdepaForm.getTransporation(), cdepaForm.getDuration(), waypointList.getId());
        final boolean wasInsertDeparturePoint = this.departurePointsMapper.insert(departurePoint) == 1;
        if (!wasInsertDeparturePoint) throw new FailedInsertException("failed to insert departurePoint");

        final var cwayFormList = form.getWaypointList();
        for (final var cwForm : cwayFormList) {
            final var waypoint = new Waypoint(-1, cwForm.getLabel(), cwForm.getPlaceId(), cwForm.getVisitOrder(), cwForm.getPreferredArrivalDatetime(), cwForm.getArrivalDatetime(), cwForm.getStayTime(), cwForm.getTransporation(), cwForm.getDuration(), cwForm.getBudget(), waypointList.getId());
            final var wasInsertWaypoint = this.waypointsMapper.insert(waypoint) == 1;
            if (!wasInsertWaypoint) throw new FailedInsertException("failed to insert waypoint");
        }

        final var cdestForm = form.getDestinationPoint();
        final var destinationPoint = new DestinationPoint(-1, cdestForm.getLabel(), cdestForm.getPlaceId(), cdestForm.getArrivalDatetime(), waypointList.getId());
        final boolean wasInsertDestinationPoint = this.destinationPointsMapper.insert(destinationPoint) == 1;
        if (!wasInsertDestinationPoint) throw new FailedInsertException("failed to insert destinationPoint");

        // 作成したものを取得
        final WaypointList createdWaypointList = this.waypointListsMapper.selectByIdAndEmail(waypointList.getId(), email);
        if (createdWaypointList == null) throw new FailedSelectException("failed to find waypointList");

        final DeparturePoint createdDeparturePoint = this.departurePointsMapper.selectByIdAndEmail(departurePoint.getId(), email);
        if (createdDeparturePoint == null) throw new FailedSelectException("failed to insert departurePoint");

        final List<Waypoint> createdWaypoints = this.waypointsMapper.selectByWaypointListIdAndEmail(waypointList.getId(), email);
        if (createdWaypoints == null) throw new FailedSelectException("failed to find waypoints");

        final DestinationPoint createdDestinationPoint = this.destinationPointsMapper.selectByIdAndEmail(destinationPoint.getId(), email);
        if (createdDestinationPoint == null) throw new FailedSelectException("failed to find destinationPoint");

        return new EditWaypointListForm(createdWaypointList, createdDeparturePoint, createdWaypoints, createdDestinationPoint);
    }
}
