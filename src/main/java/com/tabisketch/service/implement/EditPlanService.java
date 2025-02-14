package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.view_model.PlanViewModel;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.service.IEditPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditPlanService implements IEditPlanService {
    private final IPlansMapper plansMapper;

    public EditPlanService(final IPlansMapper plansMapper) {
        this.plansMapper = plansMapper;
    }

    @Override
    @Transactional
    public PlanViewModel execute(final String email, final EditPlanForm form) {
        // プラン取得
        final Plan plan = this.plansMapper.selectByUUIDAndEmail(form.getUuid(), email);
        if (plan == null) throw new FailedSelectException("failed to find plan");

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

        // データ加工
        return new PlanViewModel(newPlan);
    }
}
