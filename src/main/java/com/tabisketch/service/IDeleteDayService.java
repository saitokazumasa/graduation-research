package com.tabisketch.service;

import com.tabisketch.exception.DeleteFailedException;

public interface IDeleteDayService {
    void execute(final int id) throws DeleteFailedException;
}
