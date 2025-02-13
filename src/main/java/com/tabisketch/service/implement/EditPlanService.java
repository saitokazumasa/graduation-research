package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.view_model.PlanViewModel;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EditPlanService implements IEditPlanService {
    private final IPlansMapper plansMapper;
    private final IUsersMapper usersMapper;

    public EditPlanService(final IPlansMapper plansMapper, final IUsersMapper usersMapper) {
        this.plansMapper = plansMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public PlanViewModel execute(final String uuid, final String email, final EditPlanForm form) {
        // プラン取得
        final var _uuid = UUID.fromString(uuid);
        final Plan plan = this.plansMapper.selectByUUID(_uuid);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // ユーザー一致の確認
        if (plan.getUserId() != user.getId()) throw new FailedSelectException("failed to find plan");

        // 更新
        final var newPlan = new Plan(
                plan.getId(),
                plan.getUuid(),
                form.getTitle(),
                form.getThumbnailPath(),
                form.isEditable(),
                form.isPubliclyViewable(),
                plan.getUserId()
        );
        final boolean wasUpdatedPlan = this.plansMapper.update(newPlan) == 1;
        if (!wasUpdatedPlan) throw new FailedUpdateException("failed to update plan");

        // 最新プラン取得
        final Plan updatedPlan = this.plansMapper.selectByUUID(_uuid);
        if (updatedPlan == null) throw new FailedSelectException("failed to find plan");

        // データ加工
        return new PlanViewModel(updatedPlan);
    }
}
