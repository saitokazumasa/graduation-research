package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EditPlanServiceTest {
    @Autowired
    private IEditPlanService editPlanService;
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
        when(this.plansMapper.update(any())).thenReturn(1);

        final var uuid = plan.getUuid().toString();
        final var editPlanForm = ExampleEditPlanForm.gen();
        this.editPlanService.execute(uuid, user.getEmail(), editPlanForm);

        verify(this.plansMapper, times(2)).selectByUUID(any());
        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.plansMapper).update(any());
    }
}
