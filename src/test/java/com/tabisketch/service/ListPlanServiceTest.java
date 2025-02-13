package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.ListPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListPlanServiceTest {
    @InjectMocks
    private ListPlanService listPlanService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private IPlansMapper plansMapper;

    @Test
    public void testExecute() {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.plansMapper.selectByUserId(anyInt())).thenReturn(new ArrayList<>());

        this.listPlanService.execute(user.getEmail());

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).selectByUserId(anyInt());
    }
}
