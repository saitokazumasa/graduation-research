package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ExampleWaypointList;
import com.tabisketch.bean.form.ExampleCreateWaypointListForm;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.implement.CreateWaypointListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateWaypointListServiceTest {
    @InjectMocks
    private CreateWaypointListService createWaypointListService;
    @Mock
    private IWaypointListsMapper waypointListsMapper;
    @Mock
    private IFindOnePlanWithUserService findOnePlanWithUserService;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        final var waypointList = ExampleWaypointList.gen();
        when(this.findOnePlanWithUserService.execute(any(), anyString())).thenReturn(plan);
        when(this.waypointListsMapper.insert(any())).thenReturn(1);
        when(this.waypointListsMapper.selectById(anyInt())).thenReturn(waypointList);

        final var email = ExampleUser.gen().getEmail();
        final var createWaypointListForm = ExampleCreateWaypointListForm.gen();
        final var waypointListViewModel = this.createWaypointListService.execute(email, createWaypointListForm);
        assert waypointListViewModel != null;

        verify(this.findOnePlanWithUserService).execute(any(), anyString());
        verify(this.waypointListsMapper).insert(any());
        verify(this.waypointListsMapper).selectById(anyInt());
    }
}
