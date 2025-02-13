package com.tabisketch.service;

import com.tabisketch.bean.entity.ExamplePlan;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditPlanForm;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.implement.EditPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditPlanServiceTest {
    @InjectMocks
    private EditPlanService editPlanService;
    @Mock
    private IPlansMapper plansMapper;
    @Mock
    private IFindOnePlanWithUserService findOnePlanWithUserService;

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        when(this.findOnePlanWithUserService.execute(any(), anyString())).thenReturn(plan);
        when(this.plansMapper.update(any())).thenReturn(1);
        when(this.plansMapper.selectByUUID(any())).thenReturn(plan);

        final var uuid = plan.getUuid().toString();
        final var email = ExampleUser.gen().getEmail();
        final var editPlanForm = ExampleEditPlanForm.gen();
        final var planViewModel = this.editPlanService.execute(uuid, email, editPlanForm);
        assert planViewModel != null;

        verify(this.findOnePlanWithUserService).execute(any(), anyString());
        verify(this.plansMapper).update(any());
        verify(this.plansMapper).selectByUUID(any());
    }
}
