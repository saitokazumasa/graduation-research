package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditWaypointListForm {
    @Min(1)
    private int id;

    @NotNull
    private Transporation mainTransporation;

    @NotNull
    private UUID planUUID;
}
