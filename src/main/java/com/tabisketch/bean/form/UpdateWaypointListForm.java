package com.tabisketch.bean.form;

import com.tabisketch.constant.Transporation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateWaypointListForm {
    @Min(1)
    private int id;

    @Min(1)
    private int travelDay;

    @NotNull
    private Transporation mainTransporation;

    @Valid
    @NotNull
    private UpdateDeparturePointForm departurePoint;

    @Valid
    @NotEmpty
    private List<UpdateWaypointForm> waypointList;

    @Valid
    @NotNull
    private UpdateDestinationPointForm destinationPoint;
}
