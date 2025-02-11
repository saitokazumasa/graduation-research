package com.tabisketch.bean.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditPlanForm {
    private String title;
    private boolean editable;
    private boolean publiclyViewable;
}
