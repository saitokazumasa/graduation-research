package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.bean.form.SendVerifyEmailMailFrom;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendVerifyEmailMailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final IRegisterService registerService;
    private final ISendVerifyEmailMailService sendVerifyEmailMailService;

    public RegisterController(
            final IRegisterService registerService,
            final ISendVerifyEmailMailService sendVerifyEmailMailService
    ) {
        this.registerService = registerService;
        this.sendVerifyEmailMailService = sendVerifyEmailMailService;
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

        final var email = (String) model.getAttribute("email");
        model.addAttribute("sendVerifyEmailMailFrom", new SendVerifyEmailMailFrom(email));
        return "register/send";
    }

    @PostMapping("/send")
    public String resend(
            final @Validated SendVerifyEmailMailFrom sendVerifyEmailMailFrom,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "register/index";

        try {
            this.sendVerifyEmailMailService.execute(sendVerifyEmailMailFrom);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "register/index";
        }

        redirectAttributes.addFlashAttribute("email", sendVerifyEmailMailFrom.getEmail());
        return "redirect:/register/send";
    }
}
