package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.Waypoint;
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
public class EditWaypointForm {
    @Min(1)
    private int id;

    @NotBlank
    private String label;

    @NotBlank
    private String placeId;

    private LocalDateTime preferredArrivalDatetime;

    private LocalDateTime arrivalDatetime;

    @Min(0)
    private int stayTime;

    @NotNull
    private Transporation transporation;

    private int duration;

    @Min(0)
    private int budget;

    public EditWaypointForm(
            final int id,
            final String label,
            final String placeId,
            final int stayTime,
            final Transporation transporation,
            final int budget
    ) {
        this.id = id;
        this.label = label;
        this.placeId = placeId;
        this.stayTime = stayTime;
        this.transporation = transporation;
        this.budget = budget;
    }

    public EditWaypointForm(final Waypoint waypoint) {
        this.id = waypoint.getId();
        this.label = waypoint.getLabel();
        this.placeId = waypoint.getPlaceId();
        this.preferredArrivalDatetime = waypoint.getPreferredArrivalDatetime();
        this.arrivalDatetime = waypoint.getArrivalDatetime();
        this.stayTime = waypoint.getStayTime();
        this.transporation = waypoint.getTransporation();
        this.duration = waypoint.getDuration();
        this.budget = waypoint.getBudget();
    }
}
