package com.tabisketch.bean.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlanOutput {
    private UUID uuid;
    private String title;
    private String thumbnailPath;
    private boolean editable;
    private boolean publiclyViewable;
}
