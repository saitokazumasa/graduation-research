package com.tabisketch.service;

import com.tabisketch.bean.view_model.PlanViewModel;

import java.util.List;

public interface IListPlanService {
    List<PlanViewModel> execute(final String email);
}
