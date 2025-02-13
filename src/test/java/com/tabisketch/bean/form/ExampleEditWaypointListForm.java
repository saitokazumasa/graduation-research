package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;

import java.util.UUID;

public class ExampleEditWaypointListForm {
    private ExampleEditWaypointListForm() {
    }

    public static EditWaypointListForm gen() {
        return new EditWaypointListForm(
                1,
                Transporation.WALKING,
                UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82")
        );
    }
}
