package com.tabisketch.service;

import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.bean.view_model.WaypointListViewModel;

public interface IEditWaypointListService {
    WaypointListViewModel execute(final String email, final EditWaypointListForm form);
}
