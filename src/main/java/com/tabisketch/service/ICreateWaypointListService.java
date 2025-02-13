package com.tabisketch.service;

import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.view_model.WaypointListViewModel;

public interface ICreateWaypointListService {
    WaypointListViewModel execute(final String email, final CreateWaypointListForm form);
}
