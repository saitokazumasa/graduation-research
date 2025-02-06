package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.GooglePlace;
import com.tabisketch.bean.entity.Place;
import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.mapper.IGooglePlaceMapper;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.service.ICreatePlaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePlaceService implements ICreatePlaceService {
    private final IPlacesMapper placesMapper;
    private final IGooglePlaceMapper googlePlaceMapper;

    public CreatePlaceService(
            final IPlacesMapper placesMapper,
            final IGooglePlaceMapper googlePlaceMapper
    ) {
        this.placesMapper = placesMapper;
        this.googlePlaceMapper = googlePlaceMapper;
    }

    @Override
    @Transactional
    public int execute(final CreatePlaceForm createPlaceForm) throws InsertFailedException {
        // GooglePlaceを取得
        final GooglePlace googlePlace = this.googlePlaceMapper.selectByPlaceId(createPlaceForm.getGooglePlaceId());

        // GooglePlaceが存在する場合、Placeを追加
        if (googlePlace != null) {
            final var place = createPlaceForm.toPlace(googlePlace.getId());
            final int insertPlaceResult = this.placesMapper.insert(place);
            if (insertPlaceResult != 1) throw new InsertFailedException(Place.class.getName());

            return place.getId();
        }

        // GooglePlaceを追加
        final var createdGooglePlace = createPlaceForm.toGooglePlace();
        final int insertGooglePlaceResult = this.googlePlaceMapper.insert(createdGooglePlace);
        if (insertGooglePlaceResult != 1) throw new InsertFailedException(GooglePlace.class.getName());

        // Placeを追加
        final var place = createPlaceForm.toPlace(createdGooglePlace.getId());
        final int insertPlaceResult = this.placesMapper.insert(place);
        if (insertPlaceResult != 1) throw new InsertFailedException(Place.class.getName());

        return place.getId();
    }
}
