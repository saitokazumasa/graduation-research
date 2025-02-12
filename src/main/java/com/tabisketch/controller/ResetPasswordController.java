package com.tabisketch.controller;

import com.tabisketch.bean.form.ResetPasswordForm;
import com.tabisketch.bean.form.SendResetPasswordMailForm;
import com.tabisketch.service.IResetPasswordService;
import com.tabisketch.service.ISendResetPasswordMailService;
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
@RequestMapping("/reset-password")
public class ResetPasswordController {
    private final ISendResetPasswordMailService sendResetPasswordMailService;
    private final IResetPasswordService resetPasswordService;

    public ResetPasswordController(
            final ISendResetPasswordMailService sendResetPasswordMailService,
            final IResetPasswordService resetPasswordService
    ) {
        this.sendResetPasswordMailService = sendResetPasswordMailService;
        this.resetPasswordService = resetPasswordService;
    }

    @GetMapping
    public String get(final Model model) {
        model.addAttribute("sendResetPasswordMailForm", new SendResetPasswordMailForm());
        return "reset-password/index";
    }

    @PostMapping
    public String post(
            final @Validated SendResetPasswordMailForm sendResetPasswordMailForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "reset-password/index";

        try {
            this.sendResetPasswordMailService.execute(sendResetPasswordMailForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "reset-password/index";
        }

        redirectAttributes.addFlashAttribute("email", sendResetPasswordMailForm.getEmail());
        return "redirect:/reset-password/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("email")) return "register/index";
        return "reset-password/send";
    }

    @GetMapping("/r/{uuid}")
    public String getReset(final @PathVariable String uuid, final Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        return "reset-password/{uuid}";
    }

    @PostMapping("/r/{uuid}")
    public String postReset(
            final @PathVariable String uuid,
            final @Validated ResetPasswordForm resetPasswordForm,
            final BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return "reset-password/{uuid}";

        try {
            this.resetPasswordService.execute(uuid, resetPasswordForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "reset-password/{uuid}";
        }

        return "redirect:/reset-password/complate";
    }

    @GetMapping("/complate")
    public String complate() {
        return "reset-password/complate";
    }
}
