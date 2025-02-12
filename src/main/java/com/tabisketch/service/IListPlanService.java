package com.tabisketch.service;

import com.tabisketch.bean.output.PlanOutput;

import java.util.List;

public interface IListPlanService {
    List<PlanOutput> execute(final String email);
}
