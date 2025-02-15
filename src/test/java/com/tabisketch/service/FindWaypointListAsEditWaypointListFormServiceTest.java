package com.tabisketch.service;

import com.tabisketch.mapper.IDeparturePointsMapper;
import com.tabisketch.mapper.IDestinationPointsMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.mapper.IWaypointsMapper;
import com.tabisketch.service.implement.FindWaypointListAsEditWaypointListFormService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindWaypointListAsEditWaypointListFormServiceTest {
    @InjectMocks
    private FindWaypointListAsEditWaypointListFormService findWaypointListAsEditWaypointListFormService;
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
