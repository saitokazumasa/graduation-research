package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.mapper.IDeparturePointsMapper;
import com.tabisketch.mapper.IDestinationPointsMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.mapper.IWaypointsMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindWaypointListAsEditWaypointListFormServiceTest {
    @InjectMocks
    private IFindWaypointListAsEditWaypointListFormService findWaypointListAsEditWaypointListFormService;
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
//        final var waypointList = ExampleWaypointList.gen();
//        when(this.waypointListsMapper.selectByPlanUUIDAndTravelDayAndEmail(any(), anyInt(), anyString())).thenReturn(waypointList);
        // TODO: テスト書く
    }
}
