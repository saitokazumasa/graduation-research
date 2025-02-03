package com.tabisketch.service.implement;

import com.tabisketch.mapper.IDestinationListsMapper;
import com.tabisketch.mapper.IDestinationsMapper;
import com.tabisketch.mapper.IStartPlacesMapper;
import com.tabisketch.service.ICallGeminiService;
import com.tabisketch.service.IOptimizeRouteService;
import org.springframework.stereotype.Service;

@Service
public class OptimizeRouteService implements IOptimizeRouteService {
    private final IDestinationListsMapper destinationListsMapper;
    private final IStartPlacesMapper startPlacesMapper;
    private final IDestinationsMapper destinationsMapper;

    public OptimizeRouteService(
            final IDestinationListsMapper destinationListsMapper,
            final IStartPlacesMapper startPlacesMapper,
            final IDestinationsMapper destinationsMapper
    ) {
        this.destinationListsMapper = destinationListsMapper;
        this.startPlacesMapper = startPlacesMapper;
        this.destinationsMapper = destinationsMapper;
    }

    @Override
    public void execute(final int entityId) {
        // DBから必要な値を取得
        final var destinationList = this.destinationListsMapper.selectById(entityId);
        final var startPlace = this.startPlacesMapper.selectById(destinationList.getId());
        final var destinations = this.destinationsMapper.selectByDestinationListId(destinationList.getId());



        // TODO: JsonにしてGoogleAIに丸投げ
        // TODO: 返ってきた値をreturn
    }
}
