//package com.tabisketch.controller;
//
//import com.tabisketch.bean.entity.Plan;
//import com.tabisketch.service.IListPlanService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/plan/list")
//public class ListPlanController {
//    private final IListPlanService listPlanService;
//
//    public ListPlanController(final IListPlanService listPlanService) {
//        this.listPlanService = listPlanService;
//    }
//
//    @GetMapping
//    public String get(
//            final @AuthenticationPrincipal(expression = "username") String mailAddress,
//            final Model model
//    ) {
//        final List<Plan> planList = this.listPlanService.execute(mailAddress);
//        model.addAttribute("planList", planList);
//        return "plan/list";
//    }
//}
