package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.implement.DeletePlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeletePlanServiceTest {
    @InjectMocks
    private DeletePlanService deletePlanService;
    @Mock
    private IPlansMapper plansMapper;
    @Mock
    private IWaypointListsMapper waypointListsMapper;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        when(this.plansMapper.deleteByUUIDAndEmail(any(), anyString())).thenReturn(1);

        final String uuid = plan.getUuid().toString();
        final String email = ExampleUser.gen().getEmail();
        this.deletePlanService.execute(uuid, email);

        verify(this.waypointListsMapper).deleteByPlanUUIDAndEmail(any(), anyString());
        verify(this.plansMapper).deleteByUUIDAndEmail(any(), anyString());
    }
}
