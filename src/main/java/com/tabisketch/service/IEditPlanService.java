package com.tabisketch.service;

import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.output.PlanOutput;

public interface IEditPlanService {
    PlanOutput execute(final String uuid, final EditPlanForm form);
}
