package com.tabisketch.service;

import com.tabisketch.bean.entity.*;
import com.tabisketch.bean.form.ExampleCreateWaypointListForm;
import com.tabisketch.mapper.*;
import com.tabisketch.service.implement.CreateWaypointListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateWaypointListServiceTest {
    @InjectMocks
    private CreateWaypointListService createWaypointListService;
    @Mock
    private IPlansMapper plansMapper;
    @Mock
    private IWaypointListsMapper waypointListsMapper;
    @Mock
    private IDeparturePointsMapper departurePointsMapper;
    @Mock
    private IWaypointsMapper waypointsMapper;
    @Mock
    private IDestinationPointsMapper destinationPointsMapper;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        final var waypointList = ExampleWaypointList.gen();
        final var departurePoint = ExampleDeparturePoint.gen();
        final var waypoints = Stream.of(ExampleWaypoint.gen()).toList();
        final var destination = ExampleDestinationPoint.gen();

        when(this.plansMapper.selectByUUIDAndEmail(any(), anyString())).thenReturn(plan);
        when(this.waypointListsMapper.insert(any())).thenReturn(1);
        when(this.departurePointsMapper.insert(any())).thenReturn(1);
        when(this.waypointsMapper.insert(any())).thenReturn(1);
        when(this.destinationPointsMapper.insert(any())).thenReturn(1);
        when(this.waypointListsMapper.selectByIdAndEmail(anyInt(), anyString())).thenReturn(waypointList);
        when(this.departurePointsMapper.selectByIdAndEmail(anyInt(), anyString())).thenReturn(departurePoint);
        when(this.waypointsMapper.selectByWaypointListIdAndEmail(anyInt(), anyString())).thenReturn(waypoints);
        when(this.destinationPointsMapper.selectByIdAndEmail(anyInt(), anyString())).thenReturn(destination);

        final var email = ExampleUser.gen().getEmail();
        final var createWaypointListForm = ExampleCreateWaypointListForm.gen();
        final var waypointListViewModel = this.createWaypointListService.execute(email, createWaypointListForm);
        assert waypointListViewModel != null;

        verify(this.plansMapper).selectByUUIDAndEmail(any(), anyString());
        verify(this.waypointListsMapper).insert(any());
        verify(this.departurePointsMapper).insert(any());
        verify(this.waypointsMapper, times(waypoints.size())).insert(any());
        verify(this.destinationPointsMapper).insert(any());
        verify(this.waypointListsMapper).selectByIdAndEmail(anyInt(), anyString());
        verify(this.departurePointsMapper).selectByIdAndEmail(anyInt(), anyString());
        verify(this.waypointListsMapper, times(waypoints.size())).selectByIdAndEmail(anyInt(), anyString());
        verify(this.destinationPointsMapper).selectByIdAndEmail(anyInt(), anyString());
    }
}
