package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.DeparturePoint;
import com.tabisketch.bean.entity.DestinationPoint;
import com.tabisketch.bean.entity.Waypoint;
import com.tabisketch.bean.entity.WaypointList;
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
public class EditWaypointListForm {
    @Min(1)
    private int id;

    @NotNull
    private Transporation mainTransporation;

    @Valid
    @NotNull
    private EditDeparturePointForm departurePoint;

    @Valid
    @NotEmpty
    private List<EditWaypointForm> waypointList;

    @Valid
    @NotNull
    private EditDestinationPointForm destinationPoint;

    public EditWaypointListForm(
            final WaypointList waypointList,
            final DeparturePoint departurePoint,
            final List<Waypoint> waypoints,
            final DestinationPoint destinationPoint
    ) {
        this.id = waypointList.getId();
        this.mainTransporation = waypointList.getMainTransporation();
        this.departurePoint = new EditDeparturePointForm(departurePoint);
        this.waypointList = waypoints.stream().map(EditWaypointForm::new).toList();
        this.destinationPoint = new EditDestinationPointForm(destinationPoint);
    }
}
