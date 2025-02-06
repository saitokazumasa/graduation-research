package com.tabisketch.bean.response.implement;

import com.tabisketch.bean.response.IResponse;
import lombok.Getter;

@Getter
public class CreateDayResponse implements IResponse {
    private final String status;
    private final int dayId;

    private CreateDayResponse(final String status, final int dayId) {
        this.status = status;
        this.dayId = dayId;
    }

    public static CreateDayResponse success(final int dayId) {
        return new CreateDayResponse(Status.Success.toString(), dayId);
    }

    public static CreateDayResponse failed() {
        return new CreateDayResponse(Status.Failed.toString(), -1);
    }
}
