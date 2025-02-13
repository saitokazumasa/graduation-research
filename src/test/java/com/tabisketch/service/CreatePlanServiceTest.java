package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
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
    @Mock
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
