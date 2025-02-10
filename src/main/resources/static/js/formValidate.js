class FormValidator {
    #fieldIds;

    constructor() {
        this.#fieldIds = {
            start: ['startPlace', 'startPlaceId', 'startLat', 'startLng', 'startTime'],
            end: ['endPlace', 'endPlaceId', 'endLat', 'endLng'],
            places: ['place', 'placeId', 'placeLat', 'placeLng'],
            updateStart: ['startUpdatePlace', 'startUpdatePlaceId', 'startUpdateLat', 'startUpdateLng', 'startUpdateTime'],
            updateEnd: ['endUpdatePlace', 'endUpdateLat', 'endUpdateLng'],
            updatePlaces: ['updatePlace', 'placeUpdatePlaceId', 'placeUpdateLat', 'placeUpdateLng'],
            recommend: ['recommend', 'recommendPlaceId', 'recommendLat', 'recommendLng'],
        };
    }

    #isFilled(fields) {
        return fields.every(id => document.getElementById(id).value);
    }

    #getFieldIds(prefix, num=null) {
        if (num !== null) return [this.#fieldIds[prefix][0]+num,this.#fieldIds[prefix][1]+num,this.#fieldIds[prefix][2]+num, this.#fieldIds[prefix][3]+num];
        return this.#fieldIds[prefix];
    }

    #formCheck(prefix, num=null) {
        return this.#isFilled(this.#getFieldIds(prefix, num));
    }

    validate(modalType, num=null) {
        return this.#formCheck(modalType, num);
    }
}
