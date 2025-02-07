package com.tabisketch.bean.form;

import com.tabisketch.bean.entity.GooglePlace;
import com.tabisketch.bean.entity.Place;
import com.tabisketch.constant.Transportation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class UpdatePlaceForm {
    @Min(0)
    private int id;

    @NotBlank
    private String name;

    /**
     * GoogleMapのPlaceID
     */
    @NotBlank
    private String googlePlaceId;

    private double latitude;
    private double longitude;

    @Min(1)
    private int dayId;

    @Min(0)
    private int budget;

    /**
     * 開始時間
     */
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    /**
     * 終了時間
     */
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    /**
     * 希望開始時間
     */
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime desiredStartTime;
    /**
     * 希望終了時間
     */
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime desiredEndTime;

    private String toPolyLine;
    private Integer toTravelTime;
    private Transportation toTransportation;

    public GooglePlace toGooglePlace() {
        return GooglePlace.generate(
                this.googlePlaceId,
                this.name,
                this.latitude,
                this.longitude
        );
    }

    public Place toPlace(final int googlePlaceId) {
        return Place.generate(
                googlePlaceId,
                this.dayId,
                this.budget,
                this.startTime,
                this.endTime,
                this.desiredStartTime,
                this.desiredEndTime,
                this.toPolyLine,
                this.toTravelTime,
                this.toTransportation
        );
    }
}
