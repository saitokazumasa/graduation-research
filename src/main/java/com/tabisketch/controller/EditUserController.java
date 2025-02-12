package com.tabisketch.controller;

import com.tabisketch.bean.form.EditEmailForm;
import com.tabisketch.bean.form.EditPasswordForm;
import com.tabisketch.constant.AuthenticationPrincipalExpression;
import com.tabisketch.service.IEditPasswordService;
import com.tabisketch.service.ISendEditEmailMailService;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/user/edit")
public class EditUserController {
    private final ISendEditEmailMailService sendEditEmailMailService;
    private final IEditPasswordService editPasswordService;

    public EditUserController(
            final ISendEditEmailMailService sendEditEmailMailService,
            final IEditPasswordService editPasswordService
    ) {
        this.sendEditEmailMailService = sendEditEmailMailService;
        this.editPasswordService = editPasswordService;
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
    public String sendEmail(final HttpSession httpSession, final Model model) {
        if (!model.containsAttribute("email")) return "user/edit/email/index";
        httpSession.invalidate();
        return "user/edit/email/send";
    }

    @GetMapping("/password")
    public String getPassword(final Model model) {
        model.addAttribute("editPasswordForm", new EditPasswordForm());
        return "user/edit/password/index";
    }

    @PostMapping("/password")
    public String postPassword(
            final @AuthenticationPrincipal(expression = AuthenticationPrincipalExpression.EMAIL) String email,
            final @Validated EditPasswordForm editPasswordForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "user/edit/password/index";

        try {
            this.editPasswordService.execute(email, editPasswordForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }

        return "redirect:/user/edit/password/complate";
    }

    @GetMapping("/password/complate")
    public String complatePassword(final HttpSession httpSession) {
        httpSession.invalidate();
        return "user/edit/password/complate";
    }
}
