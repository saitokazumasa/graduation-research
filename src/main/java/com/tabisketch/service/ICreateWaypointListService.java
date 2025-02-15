package com.tabisketch.service;

import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.form.EditWaypointListForm;

public interface ICreateWaypointListService {
    EditWaypointListForm execute(final String email, final CreateWaypointListForm form);
}
