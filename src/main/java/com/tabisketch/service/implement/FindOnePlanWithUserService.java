package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.User;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IFindOnePlanWithUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOnePlanWithUserService implements IFindOnePlanWithUserService {
    private final IUsersMapper usersMapper;
    private final IPlansMapper plansMapper;

    public FindOnePlanWithUserService(final IUsersMapper usersMapper, final IPlansMapper plansMapper) {
        this.usersMapper = usersMapper;
        this.plansMapper = plansMapper;
    }

    @Override
    public Plan execute(final UUID uuid, final String email) {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // プラン取得
        final Plan plan = this.plansMapper.selectByUUID(uuid);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // ユーザー一致の確認
        if (plan.getUserId() != user.getId()) throw new FailedSelectException("failed to find plan");

        return plan;
    }
}
