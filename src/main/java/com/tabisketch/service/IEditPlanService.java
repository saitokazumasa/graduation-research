package com.tabisketch.service;

import com.tabisketch.bean.form.EditPlanForm;

public interface IEditPlanService {
    EditPlanForm execute(final String email, final EditPlanForm form);
}
