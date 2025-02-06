package com.tabisketch.bean.response.implement;

import com.tabisketch.bean.response.IResponse;
import lombok.Getter;

@Getter
public class DeletePlaceResponse implements IResponse {
    private final String status;

    private DeletePlaceResponse(final String status) {
        this.status = status;
    }


    public static DeletePlaceResponse success() {
        return new DeletePlaceResponse(Status.Success.toString());
    }

    public static DeletePlaceResponse failed() {
        return new DeletePlaceResponse(Status.Failed.toString());
    }
}
