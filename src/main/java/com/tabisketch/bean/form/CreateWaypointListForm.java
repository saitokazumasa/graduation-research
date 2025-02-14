package com.tabisketch.bean.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWaypointListForm {
    @Min(1)
    private int travelDay;

    @NotNull
    private UUID planUUID;
}
