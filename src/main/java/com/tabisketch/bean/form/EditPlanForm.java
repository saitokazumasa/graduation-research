package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.Plan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditPlanForm {
    @NotNull
    private UUID uuid;

    @NotBlank
    private String title;

    @NotNull
    private String thumbnailPath;

    private boolean editable;
    private boolean publiclyViewable;

    public EditPlanForm(final Plan plan) {
        this.uuid = plan.getUuid();
        this.title = plan.getTitle();
        this.thumbnailPath = plan.getThumbnailPath();
        this.publiclyViewable = plan.isPubliclyViewable();
        this.editable = plan.isEditable();
    }
}
