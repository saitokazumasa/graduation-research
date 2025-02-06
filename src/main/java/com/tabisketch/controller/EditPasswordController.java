package com.tabisketch.controller;

import com.tabisketch.bean.form.EditPasswordForm;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.service.IEditPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/edit/password")
public class EditPasswordController {
    private final IEditPasswordService editPasswordService;

    public EditPasswordController(final IEditPasswordService editPasswordService) {
        this.editPasswordService = editPasswordService;
    }

    @GetMapping
    public String get(final @AuthenticationPrincipal(expression = "username") String username, final Model model) {
        final var editPasswordForm = EditPasswordForm.empty();
        editPasswordForm.setMailAddress(username);
        model.addAttribute("editPasswordForm", editPasswordForm);
        return "user/edit/password/index";
    }

    @PostMapping
    public String post(
            final @Validated EditPasswordForm editPasswordForm,
            final BindingResult bindingResult
    ) throws MessagingException, SelectFailedException, InvalidPasswordException, UpdateFailedException {
        if (bindingResult.hasErrors()) return "user/edit/password/index";

        this.editPasswordService.execute(editPasswordForm);

        return "redirect:/user/edit/password/confirm";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "user/edit/password/confirm";
    }
}
