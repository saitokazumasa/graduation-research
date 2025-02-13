package com.tabisketch.controller;

import com.tabisketch.service.IEditEmailService;
import com.tabisketch.service.IVerifyEmailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mail")
public class VerifyEmailController {
    private final IVerifyEmailService verifyEmailService;
    private final IEditEmailService editEmailService;

    public VerifyEmailController(
            final IVerifyEmailService verifyEmailService,
            final IEditEmailService editEmailService
    ) {
        this.verifyEmailService = verifyEmailService;
        this.editEmailService = editEmailService;
    }

    @GetMapping("/v/{uuid}")
    public String verify(final @PathVariable String uuid) {
        this.verifyEmailService.execute(uuid);
        return "redirect:/mail/complete";
    }

    @GetMapping("/nv/{uuid}")
    public String verifyNewEmail(final @PathVariable String uuid) throws MessagingException {
        this.editEmailService.execute(uuid);
        return "redirect:/mail/complete";
    }

    @GetMapping("/complete")
    public String complete() {
        return "complete";
    }
}
