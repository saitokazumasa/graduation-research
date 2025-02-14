package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.CreatePlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatePlanServiceTest {
    @InjectMocks
    private CreatePlanService createPlanService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private IPlansMapper plansMapper;

    @Test
    public void testExecute() {
        final var user = ExampleUser.gen();
        final var plan = ExamplePlan.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.plansMapper.insert(any())).thenReturn(1);
        when(this.plansMapper.selectByUUIDAndEmail(any(), anyString())).thenReturn(plan);

        this.createPlanService.execute(user.getEmail());

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).insert(any());
        verify(this.plansMapper).selectByUUIDAndEmail(any(), anyString());
    }
}
