package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.GooglePlace;
import com.tabisketch.bean.entity.Place;
import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.form.UpdatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.service.IUpdatePlaceService;
import org.springframework.stereotype.Service;

@Service
public class UpdatePlaceService implements IUpdatePlaceService {
    private final IGooglePlaceMapper googlePlaceMapper;
    private final IPlacesMapper placesMapper;

    public UpdatePlaceService(
            final IGooglePlaceMapper googlePlaceMapper,
            final IPlacesMapper placesMapper
    ) {
        this.googlePlaceMapper = googlePlaceMapper;
        this.placesMapper = placesMapper;
    }

    @Override
    public int execute(final UpdatePlaceForm updatePlaceForm)
            throws UpdateFailedException, InsertFailedException {
        // GooglePlaceを取得
        final GooglePlace googlePlace = this.googlePlaceMapper.selectByPlaceId(updatePlaceForm.getGooglePlaceId());

        // GooglePlaceが存在する場合、Placeを更新
        if (googlePlace != null) {
            final var place = updatePlaceForm.toPlace(googlePlace.getId());
            final int updatePlaceResult = this.placesMapper.update(place);
            if (updatePlaceResult != 1) throw new UpdateFailedException(Plan.class.getName());

            return place.getId();
        }

        // GooglePlaceを追加
        final var createdGooglePlace = updatePlaceForm.toGooglePlace();
        final int insertGooglePlaceResult = this.googlePlaceMapper.insert(createdGooglePlace);
        if (insertGooglePlaceResult != 1) throw new InsertFailedException(GooglePlace.class.getName());

        // Placeを追加
        final var place = updatePlaceForm.toPlace(createdGooglePlace.getId());
        final int updatePlaceResult = this.placesMapper.update(place);
        if (updatePlaceResult != 1) throw new UpdateFailedException(Place.class.getName());

        return place.getId();
    }
}
