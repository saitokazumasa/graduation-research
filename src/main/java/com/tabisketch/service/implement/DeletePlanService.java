package com.tabisketch.service.implement;

import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.IDeletePlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePlanService implements IDeletePlanService {
    private final IPlansMapper plansMapper;
    private final IWaypointListsMapper waypointListsMapper;

    public DeletePlanService(
            final IPlansMapper plansMapper,
            final IWaypointListsMapper waypointListsMapper
    ) {
        this.plansMapper = plansMapper;
        this.waypointListsMapper = waypointListsMapper;
    }

    @Override
    @Transactional
    public void execute(final String uuid, final String email) {
        // 削除
        // TODO: 子要素を削除する
        final var _uuid = UUID.fromString(uuid);

        // 0以上のため結果の検証を行わない
        this.waypointListsMapper.deleteByPlanUUIDAndEmail(_uuid, email);

        final boolean wasDeletedPlan = this.plansMapper.deleteByUUIDAndEmail(_uuid, email) == 1;
        if (!wasDeletedPlan) throw new FailedDeleteException("failed to delete plan");
    }
}
