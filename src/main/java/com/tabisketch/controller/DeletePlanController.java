package com.tabisketch.controller;

import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IDeletePlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan/delete/{uuid}")
public class DeletePlanController {
    private final IDeletePlanService deletePlanService;

    public DeletePlanController(final IDeletePlanService deletePlanService) {
        this.deletePlanService = deletePlanService;
    }

    @PostMapping
    public String post(
            final @PathVariable String uuid,
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email
    ) {
        try {
            this.deletePlanService.execute(uuid, email);
        } catch (final Exception e) {
            return "redirect:/plan/list";
        }
        return "redirect:/plan/list";
    }
}
