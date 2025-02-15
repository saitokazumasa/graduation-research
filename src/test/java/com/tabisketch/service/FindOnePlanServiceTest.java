package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.implement.FindOnePlanService;
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
public class FindOnePlanServiceTest {
    @InjectMocks
    private FindOnePlanService findOnePlanService;
    @Mock
    private IPlansMapper plansMapper;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        when(this.plansMapper.selectByUUIDAndEmail(any(), anyString())).thenReturn(plan);

        final var email = ExampleUser.gen().getEmail();
        this.findOnePlanService.execute(plan.getUuid(), email);

        verify(this.plansMapper).selectByUUIDAndEmail(any(), anyString());
    }
}
