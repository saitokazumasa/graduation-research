package com.tabisketch.service;

import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.view_model.PlanViewModel;

public interface IEditPlanService {
    PlanViewModel execute(final String email, final EditPlanForm form);
}
