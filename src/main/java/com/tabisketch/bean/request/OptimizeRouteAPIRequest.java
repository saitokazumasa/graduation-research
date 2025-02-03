package com.tabisketch.bean.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptimizeRouteAPIRequest {
    /** 目的地リストの識別子 */
    @Min(1)
    private int destinationListId;
}
