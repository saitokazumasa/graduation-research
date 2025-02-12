package com.tabisketch.bean.form;

public class ExampleEditPlanForm {
    private ExampleEditPlanForm() {
    }

    public static EditPlanForm gen() {
        return new EditPlanForm(
                "title",
                "",
                true,
                false
        );
    }
}
