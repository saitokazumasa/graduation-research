package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IListPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListPlanService implements IListPlanService {
    private final IUsersMapper usersMapper;
    private final IPlansMapper plansMapper;

    public ListPlanService(
            final IUsersMapper usersMapper,
            final IPlansMapper plansMapper
    ) {
        this.usersMapper = usersMapper;
        this.plansMapper = plansMapper;
    }

    @Override
    @Transactional
    public List<Plan> execute(final String email) {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // プラン取得
        final List<Plan> planList = this.plansMapper.selectByUserId(user.getId());
        if (planList == null) throw new FailedSelectException("failed to find plan");

        return planList;
    }
}
