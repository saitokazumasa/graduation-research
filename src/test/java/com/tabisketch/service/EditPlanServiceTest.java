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

    @Test
    public void testExecute() {
        final var plan = ExamplePlan.gen();
        when(this.plansMapper.selectByUUIDAndEmail(any(), anyString())).thenReturn(plan);
        when(this.plansMapper.update(any())).thenReturn(1);

        final var email = ExampleUser.gen().getEmail();
        final var editPlanForm = ExampleEditPlanForm.gen();
        this.editPlanService.execute(email, editPlanForm);

        verify(this.plansMapper).selectByUUIDAndEmail(any(), anyString());
        verify(this.plansMapper).update(any());
    }
}
