package com.tabisketch.controller;

import com.tabisketch.bean.request.OptimizeRouteAPIRequest;
import com.tabisketch.bean.response.OptimizeRouteAPIResponse;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.exception.InvalidRequestBodyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimize-route")
public class OptimizeRouteAPIController {
    @PostMapping
    public OptimizeRouteAPIResponse post(
            final @RequestBody @Validated OptimizeRouteAPIRequest request,
            final BindingResult bindingResult
    ) throws InvalidRequestBodyException, FailedUpdateException, FailedSelectException {
        if (bindingResult.hasErrors()) throw new InvalidRequestBodyException("Invalid request.:" + request);



        return new OptimizeRouteAPIResponse();
    }
}
