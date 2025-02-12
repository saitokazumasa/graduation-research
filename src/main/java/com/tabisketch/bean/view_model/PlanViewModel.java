package com.tabisketch.bean.view_model;

import com.tabisketch.bean.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanViewModel {
    private UUID uuid;
    private String title;
    private String thumbnailPath;
    private boolean editable;
    private boolean publiclyViewable;

    public PlanViewModel(final Plan plan) {
        this.uuid = plan.getUuid();
        this.title = plan.getTitle();
        this.thumbnailPath = plan.getThumbnailPath();
        this.editable = plan.isEditable();
        this.publiclyViewable = plan.isPubliclyViewable();
    }
}
