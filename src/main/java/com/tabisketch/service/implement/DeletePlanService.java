package com.tabisketch.service.implement;

import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.InvalidPlanUUIDException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.IDeletePlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePlanService implements IDeletePlanService {
    private final IPlansMapper plansMapper;

    public DeletePlanService(final IPlansMapper plansMapper) {
        this.plansMapper = plansMapper;
    }

    @Override
    @Transactional
    public void execute(final String uuid) {
        // プランの存在確認
        final var _uuid = UUID.fromString(uuid);
        final boolean existPlan = this.plansMapper.selectByUUID(_uuid) != null;
        if (!existPlan) throw new InvalidPlanUUIDException("invalid plan uuid");

        // 削除
        // TODO: 子要素を削除する
        final boolean wasDeletedPlan = this.plansMapper.delete(_uuid) == 1;
        if (!wasDeletedPlan) throw new FailedDeleteException("failed to delete plan");
    }
}
