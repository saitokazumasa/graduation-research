class MapDisplayController {
    constructor() {
        this.defaultCenter = { lat: 35.675682101601765, lng: 139.66842469787593 };
        this.defaultZoom = 12;

        if (typeof google === 'undefined') {
            console.error('Google Maps APIが読み込まれていません。');
            return;
        }

        this.directionsService = new google.maps.DirectionsService();
        this.directionsRenderer = new google.maps.DirectionsRenderer({
            suppressMarkers: true // マーカーを表示しない設定
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
        } else {
            console.error(`Element with ID "${elementId}" not found.`);
        }
    }

    async displayDirectionsByPlaceIds(placeIds, travelModes, options) {
        if (placeIds.length < 2) {
            console.error('少なくとも2つのplaceIdを指定してください。');
            return;
        }

        if (travelModes.length !== placeIds.length - 1) {
            console.error('travelModesの長さはplaceIdsの長さ-1でなければなりません。');
            return;
        }

        const directionsRendererArray = [];
        let totalWalkingTime = 0;

        for (let i = 0; i < placeIds.length - 1; i++) {
            const request = {
                origin: { placeId: placeIds[i] },
                destination: { placeId: placeIds[i + 1] },
                travelMode: travelModes[i],
                drivingOptions: {
                    departureTime: new Date(Date.now()),
                    trafficModel: 'pessimistic'
                },
                avoidHighways: options.useHighway ? false : true,
                avoidFerries: options.useFerry ? false : true
            };

            await this.directionsService.route(request, (result, status) => {
                if (status === 'OK') {
                    const renderer = new google.maps.DirectionsRenderer({
                        suppressMarkers: true // マーカーを表示しない設定
                    });
                    renderer.setMap(this.directionsRenderer.getMap());
                    renderer.setDirections(result);

                    // 経路内の徒歩時間を合計
                    result.routes[0].legs[0].steps.forEach(step => {
                        if (step.travel_mode === 'WALKING') {
                            totalWalkingTime += this.calculateWalkingTime(step);
                        }
                    });

                    if (totalWalkingTime <= options.maxWalkingTime) {
                        directionsRendererArray.push(renderer);
                    } else {
                        console.warn(`合計徒歩時間が最大時間を超えました: ${totalWalkingTime} 分`);
                    }
                } else {
                    console.error('Directions request failed due to ' + status);
                }
            });
        }

        this.directionsRendererArray = directionsRendererArray;
    }

    calculateWalkingTime(step) {
        const walkingSpeed = 5; // km/h
        const distanceKm = step.distance.value / 1000; // メートルをキロメートルに変換
        const timeMinutes = (distanceKm / walkingSpeed) * 60; // 時間を分に変換
        return timeMinutes;
    }

    openPopup() {
        const popup = document.getElementById('popup');
        if (popup) {
            popup.style.display = 'flex';
            this.displayMap('sp-map');
        }
    }

    closePopup() {
        const popup = document.getElementById('popup');
        if (popup) {
            popup.style.display = 'none';
        }
    }

    initMap(placeIds, travelModes, options) {
        const map = this.displayMap('map');
        if (placeIds && placeIds.length > 0 && travelModes && travelModes.length > 0) {
            this.displayDirectionsByPlaceIds(placeIds, travelModes, options);
        } else {
            this.displayDirections({ lat: 35.681236, lng: 139.767125 }, { lat: 35.689487, lng: 139.691706 });
        }
    }

    initEventListeners() {
        const popupButton = document.querySelector('button[onclick="openPopup()"]');
        if (popupButton) {
            popupButton.addEventListener('click', (e) => {
                e.preventDefault();
                this.openPopup();
            });
        }

        const popup = document.getElementById('popup');
        if (popup) {
            popup.addEventListener('click', (e) => {
                if (e.target === popup) {
                    this.closePopup();
                }
            });
        }
    }
}

function initializeMaps(placeIds = [], travelModes = [], options = {}) {
    const initializeMap = new MapDisplayController();
    initializeMap.initMap(placeIds, travelModes, options);
}

window.initializeMaps = initializeMaps;
window.openPopup = function() {
    const initializeMap = new MapDisplayController();
    if (typeof google !== 'undefined') {
        initializeMap.openPopup();
    }
};
window.closePopup = function() {
    const initializeMap = new MapDisplayController();
    if (typeof google !== 'undefined') {
        initializeMap.closePopup();
    }
};
