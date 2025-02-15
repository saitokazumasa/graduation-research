package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.IFindOnePlanService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOnePlanService implements IFindOnePlanService {
    private final IPlansMapper plansMapper;

    public FindOnePlanService(final IPlansMapper plansMapper) {
        this.plansMapper = plansMapper;
    }

    @Override
    public Plan execute(final UUID uuid, final String email) {
        final Plan plan = this.plansMapper.selectByUUIDAndEmail(uuid, email);
        if (plan == null) throw new FailedSelectException("failed to select plan");
        return plan;
    }
}
