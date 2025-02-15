package com.tabisketch.service;

import com.tabisketch.bean.entity.Plan;

import java.util.UUID;

public interface IFindOnePlanService {
    Plan execute(final UUID uuid, final String email);
}
