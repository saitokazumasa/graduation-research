package com.tabisketch.controller;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.service.IRegisterService;
import jakarta.mail.MessagingException;
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
        model.addAttribute("registerForm", RegisterForm.empty());
        return "register/index";
    }

    @PostMapping
    public String post(
            final @Validated RegisterForm registerForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) throws InvalidMailAddressException, MessagingException, InsertFailedException {
        if (bindingResult.hasErrors()) return "register/index";

        this.registerService.execute(registerForm);

        redirectAttributes.addFlashAttribute("mailAddress", registerForm.getMailAddress());
        return "redirect:/register/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("mailAddress"))
            model.addAttribute("mailAddress", "");

        return "register/send";
    }
}
