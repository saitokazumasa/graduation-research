class MapDisplayController {
    constructor() {
        this.defaultCenter = { lat: 35.675682101601765, lng: 139.66842469787593 };
        this.defaultZoom = 12;

        if (typeof google === 'undefined') return;

        this.directionsService = new google.maps.DirectionsService();
        this.directionsRenderer = new google.maps.DirectionsRenderer({
            suppressMarkers: true,
        });

        this.initEventListeners();
    }

    displayMap(elementId, options = {}) {
        const mapOptions = {
            center: options.center || this.defaultCenter,
            zoom: options.zoom || this.defaultZoom,
        };

        const mapElement = document.getElementById(elementId);
        if (mapElement) {
            const map = new google.maps.Map(mapElement, mapOptions);
            this.directionsRenderer.setMap(map);
            return map;
        }
    }

    async displayDirectionsByPlaceIds(placeIds, travelModes, options) {
        if (!placeIds || placeIds.length < 2) return;

        if (!travelModes || travelModes.length !== placeIds.length - 1) return;

        const directionsRendererArray = [];
        let totalWalkingTime = 0;

        for (let i = 0; i < placeIds.length - 1; i++) {
            const request = {
                origin: { placeId: placeIds[i] },
                destination: { placeId: placeIds[i + 1] },
                travelMode: travelModes[i],
                drivingOptions: {
                    departureTime: new Date(Date.now()),
                    trafficModel: 'pessimistic',
                },
                avoidHighways: !options.useHighway,
                avoidFerries: !options.useFerry,
            };

            await this.directionsService.route(request, (result, status) => {
                if (status === 'OK') {
                    const renderer = new google.maps.DirectionsRenderer({
                        suppressMarkers: true,
                    });
                    renderer.setMap(this.directionsRenderer.getMap());
                    renderer.setDirections(result);

                    result.routes[0].legs[0].steps.forEach((step) => {
                        if (step.travel_mode === 'WALKING') totalWalkingTime += this.calculateWalkingTime(step);
                    });

                    if (totalWalkingTime <= options.maxWalkingTime) directionsRendererArray.push(renderer);
                } else if (status === 'ZERO_RESULTS') {

                    // 徒歩での移動を試みる
                    if (travelModes[i] !== 'WALKING') {
                        request.travelMode = 'WALKING';
                        this.directionsService.route(request, (altResult, altStatus) => {
                            if (altStatus === 'OK') {
                                const renderer = new google.maps.DirectionsRenderer({
                                    suppressMarkers: true,
                                });
                                renderer.setMap(this.directionsRenderer.getMap());
                                renderer.setDirections(altResult);
                                directionsRendererArray.push(renderer);
                            }
                        });
                    }
                }
            });
        }
    }

    calculateWalkingTime(step) {
        const walkingSpeed = 5;
        const distanceKm = step.distance.value / 1000;
        return (distanceKm / walkingSpeed) * 60;
    }

    openPopup(placeIds, travelModes, options) {
        const popup = document.getElementById('popup');
        if (popup) {
            popup.style.display = 'flex';
            this.displayMap('sp-map', options);

            // ポップアップが開かれた際に経路を表示する
            this.displayDirectionsByPlaceIds(placeIds, travelModes, options);
        }
    }

    closePopup() {
        const popup = document.getElementById('popup');
        if (popup) popup.style.display = 'none';
    }

    initMap(placeIds, travelModes, options) {
        const map = this.displayMap('map');
        if (placeIds && placeIds.length > 0 && travelModes && travelModes.length > 0) this.displayDirectionsByPlaceIds(placeIds, travelModes, options);
        else this.displayMap('map');
    }

    initEventListeners() {
        const popupButton = document.querySelector('button[onclick="openPopup()"]');
        if (popupButton) {
            popupButton.addEventListener('click', (e) => {
                e.preventDefault();
                const placeIds = ["ChIJ89TugkeMGGARDmSeJIiyWFA", "ChIJCewJkL2LGGAR3Qmk0vCTGkg", "ChIJ89TugkeMGGARDmSeJIiyWFA"];
                const travelModes = ['DRIVING', 'DRIVING'];
                const options = {
                    useHighway: true,
                    useFerry: true,
                    maxWalkingTime: 15
                };
                this.openPopup(placeIds, travelModes, options);
            });
        }

        const popup = document.getElementById('popup');
        if (popup) {
            popup.addEventListener('click', (e) => {
                if (e.target === popup) this.closePopup();
            });
        }
    }
}

function initializeMaps(placeIds = [], travelModes = [], options = {}) {
    const initializeMap = new MapDisplayController();
    initializeMap.initMap(placeIds, travelModes, options);
}

window.initializeMaps = initializeMaps;

function openPopupMap(placeIds, travelModes, options) {
    console.log('placeIds', placeIds);
    const initializeMap = new MapDisplayController();
    if (typeof google !== 'undefined') initializeMap.openPopup(placeIds, travelModes, options);
}

window.closePopup = function() {
    const initializeMap = new MapDisplayController();
    if (typeof google !== 'undefined') initializeMap.closePopup();
};
