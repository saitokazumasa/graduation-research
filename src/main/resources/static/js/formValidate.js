class FormValidator {
    #fieldIds;

    constructor() {
        this.#fieldIds = {
            start: ['startPlace', 'startPlaceId', 'startLat', 'startLng'],
            end: ['endPlace', 'endPlaceId', 'endLat', 'endLng'],
            place: ['place', 'placeId', 'placeLat', 'placeLng'],
            updateStart: ['startUpdatePlace', 'startUpdatePlaceId', 'startUpdateLat', 'startUpdateLng'],
            updateEnd: ['endUpdatePlace', 'endUpdateLat', 'endUpdateLng'],
            updatePlace: ['updatePlace', 'placeUpdatePlaceId', 'placeUpdateLat', 'placeUpdateLng']
        };
    }

    #isFilled(fields) {
        return fields.every(id => document.getElementById(`${id}`).value);
    }

    #getFieldIds(prefix, num=null) {
        if (num !== null) {
            return [this.#fieldIds[prefix][0]+num,this.#fieldIds[prefix][1]+num,this.#fieldIds[prefix][2]+num, this.#fieldIds[prefix][3]+num];
        }
        return this.#fieldIds[prefix];
    }

    #getFieldIdsWithTime(prefix) {
        return [...this.#getFieldIds(prefix), `${prefix}Time`];
    }

    #formCheck(prefix, hasTime = false, num = null) {
        return this.#isFilled(hasTime ? this.#getFieldIdsWithTime(prefix) : this.#getFieldIds(prefix, num));
    }

    #startFormCheck() {
        return this.#formCheck('start', true);
    }

    #endFormCheck() {
        return this.#formCheck('end');
    }

    #placeFormCheck(num) {
        return this.#formCheck('place', false, num);
    }

    #updateStartFormCheck() {
        return this.#formCheck('startUpdate',true);
    }

    #updateEndFormCheck() {
        return this.#formCheck('endUpdate');
    }

    #updatePlaceFormCheck(num) {
        return this.#formCheck('updatePlace', false, num);
    }

    validate(modalType, num) {
        switch (modalType) {
        case ModalType.start:
            return this.#startFormCheck();
        case ModalType.end:
            return this.#endFormCheck();
        case ModalType.places:
            return this.#placeFormCheck(num);
        case ModalType.updateStart:
            return this.#updateStartFormCheck();
        case ModalType.updateEnd:
            return this.#updateEndFormCheck();
        case ModalType.updatePlaces:
            return this.#updatePlaceFormCheck(num);
        default:
            return false;
        }
    }
}
