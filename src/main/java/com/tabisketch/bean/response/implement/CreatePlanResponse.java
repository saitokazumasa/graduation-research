package com.tabisketch.bean.response.implement;

import com.tabisketch.bean.response.IResponse;
import lombok.Getter;

@Getter
public class CreatePlanResponse implements IResponse {
    private final String status;
    private final int planId;

    private CreatePlanResponse(final String status, final int planId) {
        this.status = status;
        this.planId = planId;
    }

    public static CreatePlanResponse success(final int planId) {
        return new CreatePlanResponse(Status.Success.toString(), planId);
    }

    public static CreatePlanResponse failed() {
        return new CreatePlanResponse(Status.Failed.toString(), -1);
    }
}
