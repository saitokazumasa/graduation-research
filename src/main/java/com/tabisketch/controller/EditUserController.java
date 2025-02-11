package com.tabisketch.controller;

import com.tabisketch.bean.form.EditEmailForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IEditEmailService;
import com.tabisketch.service.ISendEditEmailMailService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user/edit")
public class EditUserController {
    private final ISendEditEmailMailService sendEditEmailMailService;
    private final IEditEmailService editEmailService;

    public EditUserController(
            final ISendEditEmailMailService sendEditEmailMailService,
            final IEditEmailService editEmailService
    ) {
        this.sendEditEmailMailService = sendEditEmailMailService;
        this.editEmailService = editEmailService;
    }

    @GetMapping
    public String get(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final Model model
    ) {
        model.addAttribute("email", email);
        return "user/edit/index";
    }

    @GetMapping("/email")
    public String getEmail(final Model model) {
        model.addAttribute("editEmailForm", new EditEmailForm());
        return "user/edit/email/index";
    }

    @PostMapping("/email")
    public String postEmail(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String currentEmail,
            final @Validated EditEmailForm editEmailForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "user/edit/email/index";

        try {
            this.sendEditEmailMailService.execute(currentEmail, editEmailForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "user/edit/email/index";
        }

        redirectAttributes.addFlashAttribute("email", editEmailForm.getNewEmail());
        return "redirect:/user/edit/email/send";
    }

    @GetMapping("/email/send")
    public String sendEmail(final Model model) {
        if (!model.containsAttribute("email")) return "user/edit/email/index";
        return "user/edit/email/send";
    }

    @GetMapping("/email/v/{uuid}")
    public String verifyEmail(final @PathVariable String uuid) {
        try {
            this.editEmailService.execute(uuid);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/user/edit/email/complate";
    }

    @GetMapping("/email/complate")
    public String complateEmail() {
        return "user/edit/email/complate";
    }
}
