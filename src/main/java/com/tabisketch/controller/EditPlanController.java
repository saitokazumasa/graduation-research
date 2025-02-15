package com.tabisketch.controller;

import com.tabisketch.bean.entity.Plan;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.bean.form.EditWaypointListForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IFindOnePlanService;
import com.tabisketch.service.IFindWaypointListAsEditWaypointListFormService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/plan/edit/{planUUID}/{waypointTravelDay}")
public class EditPlanController {
    private final IFindOnePlanService findOnePlanService;
    private final IFindWaypointListAsEditWaypointListFormService findWaypointListAsEditWaypointListFormService;

    public EditPlanController(
            final IFindOnePlanService findOnePlanService,
            final IFindWaypointListAsEditWaypointListFormService findWaypointListAsEditWaypointListFormService
    ) {
        this.findOnePlanService = findOnePlanService;
        this.findWaypointListAsEditWaypointListFormService = findWaypointListAsEditWaypointListFormService;
    }

    @GetMapping
    public String get(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @PathVariable UUID planUUID,
            final @PathVariable int waypointTravelDay,
            final Model model
    ) {
        final boolean containsEdtiPlanForm = model.containsAttribute("editPlanForm");
        final boolean containsEditWaypointListForm = model.containsAttribute("editWaypointListForm");

        if (containsEdtiPlanForm && containsEditWaypointListForm) return "plan/edit";

        try {
            final Plan plan = this.findOnePlanService.execute(planUUID, email);
            final EditWaypointListForm editWaypointListForm = this.findWaypointListAsEditWaypointListFormService.execute(planUUID, waypointTravelDay, email);
            model.addAttribute("editPlanForm", new EditPlanForm(plan));
            model.addAttribute("editWaypointListForm", editWaypointListForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "redirect: plan/list";
        }
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

//        try {
//            this.editPlanService.execute(email, editPlanForm);
//        } catch (final Exception e) {
//            System.err.println(e.getMessage());
//            return "plan/edit";
//        }

        return "redirect:/plan/edit/" + uuid;
    }
}
