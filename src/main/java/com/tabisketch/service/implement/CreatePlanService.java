package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ICreatePlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreatePlanService implements ICreatePlanService {
    private final IUsersMapper usersMapper;
    private final IPlansMapper plansMapper;

    public CreatePlanService(
            final IUsersMapper usersMapper,
            final IPlansMapper plansMapper
    ) {
        this.usersMapper = usersMapper;
        this.plansMapper = plansMapper;
    }

    @Override
    @Transactional
    public EditPlanForm execute(final String email) {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // 追加
        final var plan = new Plan(-1, UUID.randomUUID(), "", "", true, false, user.getId());
        final boolean wasInsertUser = this.plansMapper.insert(plan) == 1;
        if (!wasInsertUser) throw new FailedInsertException("failed to insert plan");

        // 作成したプランを加工してリターン
        final var created = this.plansMapper.selectByUUIDAndEmail(plan.getUuid(), email);
        return new EditPlanForm(created);
    }
}
