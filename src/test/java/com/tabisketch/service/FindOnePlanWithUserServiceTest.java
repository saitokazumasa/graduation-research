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
public class FindOnePlanWithUserServiceTest {
    @Autowired
    private IFindOnePlanWithUserService findOnePlanWithUserService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IPlansMapper plansMapper;

    @Test
    public void testExecute() {
        final var user = ExampleUser.gen();
        final var plan = ExamplePlan.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.plansMapper.selectByUUID(any())).thenReturn(plan);

        final var returned = this.findOnePlanWithUserService.execute(plan.getUuid(), user.getEmail());
        assert returned == plan;

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).selectByUUID(any());
    }
}
