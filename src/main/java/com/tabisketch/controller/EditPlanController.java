package com.tabisketch.controller;

import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IEditPlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan/edit/{uuid}")
public class EditPlanController {
    private final IEditPlanService editPlanService;

    public EditPlanController(final IEditPlanService editPlanService) {
        this.editPlanService = editPlanService;
    }

    @GetMapping
    public String get(final Model model) {
        model.addAttribute("editPlanForm", new EditPlanForm());
        return "plan/edit";
    }

    @PostMapping
    public String post(
            final @PathVariable String uuid,
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @Validated EditPlanForm editPlanForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "plan/edit";

        try {
            this.editPlanService.execute(email, editPlanForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "plan/edit";
        }

        return "redirect:/plan/edit/" + uuid;
    }
}
