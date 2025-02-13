package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IRegisterService;
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

    public RegisterController(final IRegisterService registerService) {
        this.registerService = registerService;
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
}
