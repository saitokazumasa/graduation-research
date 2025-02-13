package com.tabisketch.rest_controller;

import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IDeletePlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan/delete/{uuid}")
public class DeletePlanRestController {
    private final IDeletePlanService deletePlanService;

    public DeletePlanRestController(final IDeletePlanService deletePlanService) {
        this.deletePlanService = deletePlanService;
    }

    @PostMapping
    public void post(
            final @PathVariable String uuid,
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email
    ) {
        this.deletePlanService.execute(uuid, email);
    }
}
