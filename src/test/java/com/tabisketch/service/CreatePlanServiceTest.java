package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreatePlanServiceTest {
    @Autowired
    private ICreatePlanService createPlanService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IPlansMapper plansMapper;
    @MockitoBean
    private IWaypointListsMapper waypointListsMapper;

    @Test
    public void testExecute() {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.plansMapper.insert(any())).thenReturn(1);
        when(this.waypointListsMapper.insert(any())).thenReturn(1);

        final String uuid = this.createPlanService.execute(user.getEmail());
        assert !uuid.isBlank();

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).insert(any());
        verify(this.waypointListsMapper).insert(any());
    }
}
