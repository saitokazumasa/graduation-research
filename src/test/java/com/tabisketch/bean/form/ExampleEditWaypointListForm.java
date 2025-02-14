package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;

import java.util.UUID;

public class ExampleEditWaypointListForm {
    private ExampleEditWaypointListForm() {
    }

    public static EditWaypointListForm gen() {
        return new EditWaypointListForm(
                1,
                Transporation.WALK,
                // TODO: nullでなく、きちんと生成する
                null,
                null,
                null
        );
    }
}
