package com.tabisketch.controller;

import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.service.IEditMailAddressService;
import jakarta.mail.MessagingException;
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
@RequestMapping("/user/edit/mail")
public class EditMailAddressController {
    private final IEditMailAddressService editMailAddressService;

    public EditMailAddressController(final IEditMailAddressService editMailAddressService) {
        this.editMailAddressService = editMailAddressService;
    }

    @GetMapping
    public String get(final @AuthenticationPrincipal(expression = "username") String username, final Model model) {
        final var editMailAddressForm = EditMailAddressForm.empty();
        editMailAddressForm.setCurrentMailAddress(username);
        model.addAttribute("editMailAddressForm", editMailAddressForm);
        return "user/edit/mail/index";
    }

    @PostMapping
    public String post(
            final @Validated EditMailAddressForm editMailAddressForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) throws InvalidMailAddressException, MessagingException, SelectFailedException, InvalidPasswordException, InsertFailedException {
        if (bindingResult.hasErrors()) return "user/edit/mail/index";

        this.editMailAddressService.execute(editMailAddressForm);

        redirectAttributes.addFlashAttribute("mailAddress", editMailAddressForm.getNewMailAddress());
        return "redirect:/user/edit/mail/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("mailAddress"))
            model.addAttribute("mailAddress", "");

        return "user/edit/mail/send";
    }
}
