package com.tabisketch.controller;

import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.ICreatePlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan/create")
public class CreatePlanController {
    private final ICreatePlanService createPlanService;

    public CreatePlanController(final ICreatePlanService createPlanService) {
        this.createPlanService = createPlanService;
    }

    @GetMapping
    public String get(final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email) {
        final String uuid = this.createPlanService.execute(email);
        return "redirect:/plan/edit/" + uuid;
    }
}
