package com.tabisketch.service;

import com.tabisketch.bean.entity.Plan;

import java.util.List;

public interface IListPlanService {
    List<Plan> execute(final String email);
}
