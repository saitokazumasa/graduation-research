package com.tabisketch.service;

import com.tabisketch.bean.entity.WaypointList;
import com.tabisketch.bean.form.CreateWaypointListForm;

public interface ICreateWaypointListService {
    WaypointList execute(final String email, final CreateWaypointListForm form);
}
