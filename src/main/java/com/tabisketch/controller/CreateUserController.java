package com.tabisketch.controller;

import com.tabisketch.bean.form.CreateEmailVerificationTokenForm;
import com.tabisketch.bean.form.CreateUserForm;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.service.ICreateEmailVerificationTokenService;
import com.tabisketch.service.ICreateUserService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
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
public class CreateUserController {
    private final ICreateUserService createUserService;
    private final ICreateEmailVerificationTokenService createEmailVerificationTokenService;
    private final ISendMailService sendMailService;
    private final String tabisketchEmail;

    public CreateUserController(
            final ICreateUserService createUserService,
            final ICreateEmailVerificationTokenService createEmailVerificationTokenService,
            final ISendMailService sendMailService,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.createUserService = createUserService;
        this.createEmailVerificationTokenService = createEmailVerificationTokenService;
        this.sendMailService = sendMailService;
        this.tabisketchEmail = tabisketchEmail;
    }

    @GetMapping
    public String get(final Model model) {
        model.addAttribute("createUserForm", new CreateUserForm());
        return "register/index";
    }

    @PostMapping
    public String post(
            final @Validated CreateUserForm createUserForm,
            final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) return "register/index";

        // ユーザーを作成
        final var user = this.createUserService.execute(createUserForm);

        // メールアドレス認証トークンを作成
        final var createEmailVerificationTokeForm = new CreateEmailVerificationTokenForm(user.getId());
        final var emailVerificationToken = this.createEmailVerificationTokenService.execute(createEmailVerificationTokeForm);

        // 認証メールを送信
        final var token = emailVerificationToken.getUuid().toString();
        final var sendMailForm = SendMailForm.genCreateUserMail(this.tabisketchEmail, user.getEmail(), token);
        try {
            this.sendMailService.execute(sendMailForm);
        } catch (final MessagingException e) {
            System.err.println(e.getMessage());
        }

        redirectAttributes.addFlashAttribute("email", createUserForm.getEmail());
        return "redirect:/register/send";
    }

    @GetMapping("/send")
    public String send(final Model model) {
        if (!model.containsAttribute("email")) return "register/index";

        return "register/send";
    }
}
