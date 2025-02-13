package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.entity.WaypointList;
import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.view_model.WaypointListViewModel;
import com.tabisketch.constant.Transporation;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IPlansMapper;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.ICreateWaypointListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateWaypointListService implements ICreateWaypointListService {
    private final IWaypointListsMapper waypointListsMapper;
    private final IPlansMapper plansMapper;

    public CreateWaypointListService(
            final IWaypointListsMapper waypointListsMapper,
            final IPlansMapper plansMapper
    ) {
        this.waypointListsMapper = waypointListsMapper;
        this.plansMapper = plansMapper;
    }

    @Override
    @Transactional
    public WaypointListViewModel execute(final String email, final CreateWaypointListForm form) {
        // プラン取得
        final Plan plan = this.plansMapper.selectByUUIDAndEmail(form.getPlanUUID(), email);
        if (plan == null) throw new FailedSelectException("failed to find plan");

        // 行先リスト作成
        final var waypointList = new WaypointList(-1, form.getTravelDay(), Transporation.WALK, plan.getId());
        final boolean wasInsertWaypointList = this.waypointListsMapper.insert(waypointList) == 1;
        if (!wasInsertWaypointList) throw new FailedInsertException("failed to insert waypointList");

        // 作成した行先リストを取得
        final WaypointList created = this.waypointListsMapper.selectByIdAndEmail(waypointList.getId(), email);
        if (created == null) throw new FailedSelectException("failed to find waypointList");

        // データ加工
        return new WaypointListViewModel(created);
    }
}
