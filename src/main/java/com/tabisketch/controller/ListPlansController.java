package com.tabisketch.controller;

import com.tabisketch.bean.view_model.PlanViewModel;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IListPlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/plan/list")
public class ListPlansController {
    private final IListPlanService listPlanService;

    public ListPlansController(final IListPlanService listPlanService) {
        this.listPlanService = listPlanService;
    }

    @GetMapping
    public String get(final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email, final Model model) {
        final List<PlanViewModel> planList = this.listPlanService.execute(email);
        model.addAttribute("planList", planList);
        return "plan/list";
    }
}
