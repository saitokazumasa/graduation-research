package com.tabisketch.rest_controller;

import com.tabisketch.service.IDeletePlanService;
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
    public void post(final @PathVariable String uuid) {
        try {
            this.deletePlanService.execute(uuid);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
