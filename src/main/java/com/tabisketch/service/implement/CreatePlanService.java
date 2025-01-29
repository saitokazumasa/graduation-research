package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.CreatePlanForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ICreatePlanService;
import org.springframework.stereotype.Service;

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
    public int execute(final CreatePlanForm createPlanForm) throws InsertFailedException, SelectFailedException {
        // Userが存在しなければエラー
        final User user = this.usersMapper.selectByMailAddress(createPlanForm.getUserMailAddress());
        if (user == null) throw new SelectFailedException("Userの取得に失敗しました。");

        // Planを追加
        final var plan = createPlanForm.toPlan(user.getId());
        final int result = this.plansMapper.insert(plan);
        if (result != 1) throw new InsertFailedException("Planの追加に失敗しました。");

        return plan.getId();
    }
}
