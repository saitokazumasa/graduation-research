package com.tabisketch.google.bean.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tabisketch.google.bean.valueobject.*;
import com.tabisketch.google.constant.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@Data
public class RouteMatrixAPIRequest {
    /// 出発地点の配列
    private RouteMatrixOrigin[] origins;
    /// 目的地の配列
    private RouteMatrixDestination[] destinations;
    /// 移動手段
    private RouteTravelMode travelMode;
    /// ルートの計算方法
    private RoutingPreference routingPreference;
    /// 出発日時
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime departureTime;
    /// 公共交通機関オプション
    private TransitPreferences transitPreferences;

    public RouteMatrixAPIRequest(
            final RouteMatrixOrigin[] origins,
            final RouteMatrixDestination[] destinations,
            final RouteTravelMode travelMode,
            final RoutingPreference routingPreference,
            final LocalDateTime departureTime,
            final TransitPreferences transitPreferences
    ) {
        assert origins != null && origins.length >= 1;
        assert destinations != null && destinations.length >= 1;
        assert travelMode != null;
        if (travelMode == RouteTravelMode.DRIVE) {
            assert routingPreference != null;
        } else {
            assert routingPreference == null;
        }
        assert departureTime != null;
        assert transitPreferences != null;

        this.origins = origins;
        this.destinations = destinations;
        this.travelMode = travelMode;
        this.routingPreference = routingPreference;
        this.departureTime = departureTime;
        this.transitPreferences = transitPreferences;
    }

    @AllArgsConstructor
    public static class Builder {
        private ArrayList<RouteMatrixOrigin> origins;
        private ArrayList<RouteMatrixDestination> destinations;
        private RouteTravelMode travelMode;
        private RoutingPreference routingPreference;
        private LocalDateTime departureTime;
        private TransitPreferences transitPreferences;

        public Builder() {
            this.origins = new ArrayList<>();
            this.destinations = new ArrayList<>();
        }

        public Builder addOrigin(
                final String placeId,
                final boolean avoidTolls,
                final boolean avoidHighways,
                final boolean avoidFerries,
                final boolean avoidIndoor
        ) {
            final var routeMatrixOrigin = new RouteMatrixOrigin(
                    new Waypoint(
                            false,
                            false,
                            false,
                            placeId
                    ),
                    new RouteModifiers(
                            avoidTolls,
                            avoidHighways,
                            avoidFerries,
                            avoidIndoor,
                            new VehicleInfo(VehicleEmissionType.VEHICLE_EMISSION_TYPE_UNSPECIFIED),
                            new TollPass[]{TollPass.JP_ETC, TollPass.JP_ETC2}
                    )
            );

            final var list = new ArrayList<>(this.origins);
            list.add(routeMatrixOrigin);

            return new Builder(
                    list,
                    this.destinations,
                    this.travelMode,
                    this.routingPreference,
                    this.departureTime,
                    this.transitPreferences
            );
        }

        public Builder addDestination(final String placeId) {
            final var routeMatrixDestination = new RouteMatrixDestination(
                    new Waypoint(
                            false,
                            false,
                            false,
                            placeId
                    )
            );

            final var list = new ArrayList<>(this.destinations);
            list.add(routeMatrixDestination);

            return new Builder(
                    this.origins,
                    list,
                    this.travelMode,
                    this.routingPreference,
                    this.departureTime,
                    this.transitPreferences
            );
        }

        public Builder setTravelMode(final RouteTravelMode travelMode) {
            return new Builder(
                    this.origins,
                    this.destinations,
                    travelMode,
                    this.routingPreference,
                    this.departureTime,
                    this.transitPreferences
            );
        }

        public Builder setRoutingPreference(final RoutingPreference routingPreference) {
            return new Builder(
                    this.origins,
                    this.destinations,
                    this.travelMode,
                    routingPreference,
                    this.departureTime,
                    this.transitPreferences
            );
        }

        public Builder setDepartureTime(final LocalDateTime departureTime) {
            return new Builder(
                    this.origins,
                    this.destinations,
                    this.travelMode,
                    this.routingPreference,
                    departureTime,
                    this.transitPreferences
            );
        }

        public Builder setTransitPreferences(final TransitTravelMode... allowedTravelModes) {
            final var transitPreferences = new TransitPreferences(
                    allowedTravelModes,
                    TransitRoutingPreference.TRANSIT_ROUTING_PREFERENCE_UNSPECIFIED
            );

            return new Builder(
                    this.origins,
                    this.destinations,
                    this.travelMode,
                    this.routingPreference,
                    this.departureTime,
                    transitPreferences
            );
        }

        public RouteMatrixAPIRequest build() {
            return new RouteMatrixAPIRequest(
                    this.origins.toArray(new RouteMatrixOrigin[0]),
                    this.destinations.toArray(new RouteMatrixDestination[0]),
                    this.travelMode,
                    this.routingPreference,
                    this.departureTime,
                    this.transitPreferences
            );
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
