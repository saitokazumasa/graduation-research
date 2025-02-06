package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.Place;
import com.tabisketch.bean.form.DeletePlaceForm;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.mapper.IPlacesMapper;
import com.tabisketch.service.IDeletePlaceService;
import org.springframework.stereotype.Service;

@Service
public class DeletePlaceService implements IDeletePlaceService {
    private final IPlacesMapper placesMapper;

    public DeletePlaceService(final IPlacesMapper placesMapper) {
        this.placesMapper = placesMapper;
    }

    @Override
    public void execute(final DeletePlaceForm deletePlaceForm) throws DeleteFailedException {
        final int deletePlaceResult = this.placesMapper.deleteById(deletePlaceForm.getPlaceId());
        if (deletePlaceResult != 1) throw new DeleteFailedException(Place.class.getName());
    }
}
