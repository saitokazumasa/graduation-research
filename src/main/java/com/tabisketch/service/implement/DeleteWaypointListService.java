package com.tabisketch.service.implement;

import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.mapper.IWaypointListsMapper;
import com.tabisketch.service.IDeleteWaypointListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteWaypointListService implements IDeleteWaypointListService {
    private final IWaypointListsMapper waypointListsMapper;

    public DeleteWaypointListService(final IWaypointListsMapper waypointListsMapper) {
        this.waypointListsMapper = waypointListsMapper;
    }

    @Override
    @Transactional
    public void execute(final int id, final String email) {
        // 削除
        // TODO: 子要素の削除

        final boolean wasDeleteWaypointList = this.waypointListsMapper.deleteByIdAndEmail(id, email) == 1;
        if (!wasDeleteWaypointList) throw new FailedDeleteException("failed to delete waypoint list");
    }
}
