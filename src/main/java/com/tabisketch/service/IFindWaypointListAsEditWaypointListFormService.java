package com.tabisketch.service;

import com.tabisketch.bean.form.EditWaypointListForm;

import java.util.UUID;

public interface IFindWaypointListAsEditWaypointListFormService {
    EditWaypointListForm execute(final UUID planUUID, final int travelDay, final String email);
}
