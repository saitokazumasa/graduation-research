package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.WaypointList;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.bean.view_model.WaypointListViewModel;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.IEditWaypointListService;
import org.springframework.stereotype.Service;

@Service
public class EditWaypointListService implements IEditWaypointListService {
    private final IWaypointListsMapper waypointListsMapper;

    public EditWaypointListService(
            final IWaypointListsMapper waypointListsMapper
    ) {
        this.waypointListsMapper = waypointListsMapper;
    }

    @Override
    public WaypointListViewModel execute(final String email, final EditWaypointListForm form) {
        // 行先リスト取得
        final WaypointList waypointList = this.waypointListsMapper.selectByIdAndEmail(form.getId(), email);
        if (waypointList == null) throw new FailedSelectException("failed to find waypointList");

        // 更新
        final var newWaypointList = new WaypointList(
                waypointList.getId(),
                waypointList.getTravelDay(),
                form.getMainTransporation(),
                waypointList.getPlanId()
        );
        this.waypointListsMapper.update(newWaypointList, email);

        // データ加工
        return new WaypointListViewModel(newWaypointList);
    }
}
