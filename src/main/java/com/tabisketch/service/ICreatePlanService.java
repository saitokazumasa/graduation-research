package com.tabisketch.service;

import com.tabisketch.bean.form.EditPlanForm;

public interface ICreatePlanService {
    /// @return 作成したPlanの編集用Form
    EditPlanForm execute(final String email);
}
