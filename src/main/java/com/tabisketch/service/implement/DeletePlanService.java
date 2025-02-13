package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IDeletePlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeletePlanService implements IDeletePlanService {
    private final IPlansMapper plansMapper;
    private final IUsersMapper usersMapper;

    public DeletePlanService(final IPlansMapper plansMapper, final IUsersMapper usersMapper) {
        this.plansMapper = plansMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public void execute(final String uuid, final String email) {
        // プラン取得
        final var _uuid = UUID.fromString(uuid);
        final Plan plan = this.plansMapper.selectByUUID(_uuid);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // ユーザー一致の確認
        if (plan.getUserId() != user.getId()) throw new FailedSelectException("failed to find plan");

        // 削除
        // TODO: 子要素を削除する
        final boolean wasDeletedPlan = this.plansMapper.delete(_uuid) == 1;
        if (!wasDeletedPlan) throw new FailedDeleteException("failed to delete plan");
    }
}
