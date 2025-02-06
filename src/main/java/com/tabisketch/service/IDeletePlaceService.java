package com.tabisketch.service;

import com.tabisketch.bean.form.DeletePlaceForm;
import com.tabisketch.exception.DeleteFailedException;

public interface IDeletePlaceService {
    void execute(final DeletePlaceForm deletePlaceForm) throws DeleteFailedException;
}
