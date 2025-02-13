package com.tabisketch.service;

import com.tabisketch.bean.entity.Plan;

import java.util.UUID;

/// 所有者を検証した上でPlanを取得する機能
public interface IFindOnePlanWithUserService {
    Plan execute(final UUID uuid, final String email);
}
