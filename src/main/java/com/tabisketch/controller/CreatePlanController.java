package com.tabisketch.controller;

import com.tabisketch.bean.form.CreateWaypointListForm;
import com.tabisketch.bean.form.EditPlanForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.ICreatePlanService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/plan/create")
public class CreatePlanController {
    private final ICreatePlanService createPlanService;

    public CreatePlanController(
            final ICreatePlanService createPlanService
    ) {
        this.createPlanService = createPlanService;
    }

    @GetMapping
    public String get(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final Model model
    ) {
        try {
            final EditPlanForm editPlanForm = this.createPlanService.execute(email);
            model.addAttribute("editPlanForm", editPlanForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "redirect:/plan/list";
        }

        model.addAttribute("createWaypointListForm", new CreateWaypointListForm());
        return "plan/create";
    }

    @PostMapping
    public String post(
            final @Validated EditPlanForm editPlanForm,
            final BindingResult bindingResult1,
            final @Validated CreateWaypointListForm createWaypointListForm,
            final BindingResult bindingResult2,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult1.hasErrors()) return "plan/create";
        if (bindingResult2.hasErrors()) return "plan/create";

        // TODO: 経路最適化
        // TODO: DBに保存

        final var uuid = "";
        return "redirect:/plan/edit" + uuid;
    }
}
