package com.tabisketch.api;

import com.tabisketch.bean.response.implement.CreatePlanResponse;
import com.tabisketch.bean.form.CreatePlanForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.service.ICreatePlanService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/create-plan")
public class CreatePlanAPIController {
    private final ICreatePlanService createPlanService;

    public CreatePlanAPIController(final ICreatePlanService createPlanService) {
        this.createPlanService = createPlanService;
    }

    @PostMapping
    public CreatePlanResponse post(
            final @Validated CreatePlanForm createPlanForm,
            final BindingResult bindingResult
    ) throws InsertFailedException, SelectFailedException {
        if (bindingResult.hasErrors()) return CreatePlanResponse.failed();

        final int planId = this.createPlanService.execute(createPlanForm);
        return CreatePlanResponse.success(planId);
    }
}
