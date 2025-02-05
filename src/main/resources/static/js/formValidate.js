class FormValidator {
    #isFilled(fields) {
        return fields.every(id => document.getElementById(`${id}`).value);
    }

    #getFieldIds(prefix, num) {
        if (prefix.startsWith('place'))
            return [`place${num}`, `placeId${num}`, `placeLat${num}`, `placeLng${num}`];
        if (prefix.startsWith('updatePlace'))
            return [`updatePlace${num}`, `placeUpdatePlaceId${num}`, `placeUpdateLat${num}`, `placeUpdateLng${num}`, `placeUpdateLng${num}`];
        return [`${prefix}Place`, `${prefix}PlaceId`, `${prefix}Lat`, `${prefix}Lng`];
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
