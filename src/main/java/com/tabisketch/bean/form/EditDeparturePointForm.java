package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.DeparturePoint;
import com.tabisketch.constant.Transporation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditDeparturePointForm {
    @Min(1)
    private int id;

    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    private LocalDateTime departureDatetime;

    @NotNull
    private Transporation transporation;

    private int duration;

    public EditDeparturePointForm(
            final int id,
            final String label,
            final String placeId,
            final LocalDateTime departureDatetime,
            final Transporation transporation
    ) {
        this.id = id;
        this.label = label;
        this.placeId = placeId;
        this.departureDatetime = departureDatetime;
        this.transporation = transporation;
    }

    public EditDeparturePointForm(final DeparturePoint departurePoint) {
        this.id = departurePoint.getId();
        this.label = departurePoint.getLabel();
        this.placeId = departurePoint.getPlaceId();
        this.departureDatetime = departurePoint.getDepartureDatetime();
        this.transporation = departurePoint.getTransporation();
        this.duration = departurePoint.getDuration();
    }
}
