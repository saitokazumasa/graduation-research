package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.IDeletePlanService;
import com.tabisketch.service.IFindOnePlanWithUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePlanService implements IDeletePlanService {
    private final IPlansMapper plansMapper;
    private final IFindOnePlanWithUserService findOnePlanWithUserService;

    public DeletePlanService(
            final IPlansMapper plansMapper,
            final IFindOnePlanWithUserService findOnePlanWithUserService
    ) {
        this.plansMapper = plansMapper;
        this.findOnePlanWithUserService = findOnePlanWithUserService;
    }

    @Override
    @Transactional
    public void execute(final String uuid, final String email) {
        // プラン取得
        final var _uuid = UUID.fromString(uuid);
        final Plan plan = this.findOnePlanWithUserService.execute(_uuid, email);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // 削除
        // TODO: 子要素を削除する
        final boolean wasDeletedPlan = this.plansMapper.delete(_uuid) == 1;
        if (!wasDeletedPlan) throw new FailedDeleteException("failed to delete plan");
    }
}
