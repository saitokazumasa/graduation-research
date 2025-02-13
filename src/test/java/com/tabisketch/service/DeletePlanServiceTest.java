package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeletePlanServiceTest {
    @Autowired
    private IDeletePlanService deletePlanService;
    @MockitoBean
    private IPlansMapper plansMapper;
    @MockitoBean
    private IUsersMapper usersMapper;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        final var user = ExampleUser.gen();
        when(this.plansMapper.selectByUUID(any())).thenReturn(plan);
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.plansMapper.delete(any())).thenReturn(1);

        final String uuid = plan.getUuid().toString();
        this.deletePlanService.execute(uuid, user.getEmail());

        verify(this.plansMapper).selectByUUID(any());
        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).delete(any());
    }
}
