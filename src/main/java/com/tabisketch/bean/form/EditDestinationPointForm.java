package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.DestinationPoint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditDestinationPointForm {
    @Min(1)
    private int id;

    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    private LocalDateTime arrivalDatetime;

    public EditDestinationPointForm(
            final int id,
            final String label,
            final String placeId
    ) {
        this.id = id;
        this.label = label;
        this.placeId = placeId;
    }

    public EditDestinationPointForm(final DestinationPoint destinationPoint) {
        this.id = destinationPoint.getId();
        this.label = destinationPoint.getLabel();
        this.placeId = destinationPoint.getPlaceId();
        this.arrivalDatetime = destinationPoint.getArrivalDatetime();
    }
}
