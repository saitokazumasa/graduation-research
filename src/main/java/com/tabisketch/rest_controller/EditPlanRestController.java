package com.tabisketch.rest_controller;

import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.view_model.PlanViewModel;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.exception.InvalidFormException;
import com.tabisketch.service.IEditPlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan/edit/{uuid}")
public class EditPlanRestController {
    private final IEditPlanService editPlanService;

    public EditPlanRestController(final IEditPlanService editPlanService) {
        this.editPlanService = editPlanService;
    }

    @PostMapping
    public PlanViewModel post(
            final @PathVariable String uuid,
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @Validated EditPlanForm editPlanForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) throw new InvalidFormException("invalid form");

        return this.editPlanService.execute(uuid, email, editPlanForm);
    }
}
