package com.tabisketch.bean.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDestinationPointForm {
    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    private LocalDateTime arrivalDatetime;

    public CreateDestinationPointForm(final String label, final String placeId) {
        this.label = label;
        this.placeId = placeId;
    }
}
