package com.tabisketch.service;

import com.tabisketch.bean.entity.WaypointList;
import com.tabisketch.bean.form.EditWaypointListForm;

public interface IEditWaypointListService {
    WaypointList execute(final String email, final EditWaypointListForm form);
}
