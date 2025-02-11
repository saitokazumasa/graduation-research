package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.IVerifyEmailService;
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
@RequestMapping("/register")
public class RegisterController {
    private final IRegisterService registerService;
    private final IVerifyEmailService verifyEmailService;

    public RegisterController(
            final IRegisterService registerService,
            final IVerifyEmailService verifyEmailService
    ) {
        this.registerService = registerService;
        this.verifyEmailService = verifyEmailService;
    }

    @GetMapping
    public String get(final Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register/index";
    }

    @PostMapping
    public String post(
            final @Validated RegisterForm registerForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "register/index";

        try {
            this.registerService.execute(registerForm);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "register/index";
        }

        redirectAttributes.addFlashAttribute("email", registerForm.getEmail());
        return "redirect:/register/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("email")) return "register/index";
        return "register/send";
    }

    @GetMapping("/v/{uuid}")
    public String verify(final @PathVariable String uuid) {
        try {
            this.verifyEmailService.execute(uuid);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            return "register/index";
        }
        return "redirect:/register/complate";
    }

    @GetMapping("/complate")
    public String complate() {
        return "register/complate";
    }
}
