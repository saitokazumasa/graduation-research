package com.tabisketch.bean.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDestinationPointForm {
    @Min(1)
    private int id;

    @NotBlank
    private String label;

    @NotBlank
    private String placeId;
}
