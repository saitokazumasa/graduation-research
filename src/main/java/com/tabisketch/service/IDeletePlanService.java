package com.tabisketch.service;

import java.util.UUID;

public interface IDeletePlanService {
    void execute(final UUID uuid, final String email);
}
