package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.ListPlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ListPlanServiceTest {
    @Autowired
    private ListPlanService listPlanService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
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
