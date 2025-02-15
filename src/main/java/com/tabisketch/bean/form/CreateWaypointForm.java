package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWaypointForm {
    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    private int visitOrder;

    private LocalDateTime preferredArrivalDatetime;

    private LocalDateTime arrivalDatetime;

    @Min(0)
    private int stayTime;

    private Transporation transporation;

    private int duration;

    @Min(0)
    private int budget;

    public CreateWaypointForm(
            final String label,
            final String placeId,
            final LocalDateTime preferredArrivalDatetime,
            final int stayTime,
            final int budget
    ) {
        this.label = label;
        this.placeId = placeId;
        this.preferredArrivalDatetime = preferredArrivalDatetime;
        this.stayTime = stayTime;
        this.budget = budget;
    }
}
