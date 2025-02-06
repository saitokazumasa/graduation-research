package com.tabisketch.bean.form;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePlaceForm {
    @Min(0)
    private int placeId;
}
