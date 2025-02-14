package com.tabisketch.service;

import com.tabisketch.bean.entity.Plan;

public interface ICreatePlanService {
    /// @return 作成したPlan
    Plan execute(final String email);
}
