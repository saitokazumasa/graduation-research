package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.FindOnePlanWithUserService;
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
public class FindOnePlanWithUserServiceTest {
    @InjectMocks
    private FindOnePlanWithUserService findOnePlanWithUserService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
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
