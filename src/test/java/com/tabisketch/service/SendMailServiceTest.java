package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

@SpringBootTest
public class SendMailServiceTest {
    @Value("${SITE_URL}")
    private String siteURL;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    @MockitoBean
    private JavaMailSender __; // DIで使用している
    @Autowired
    private ISendMailService sendMailService;

    // NOTE: アドレスエラーは検出されない

    @Test
    public void testExecuteMailAddressAuthMail() throws MessagingException {
        final var toMailAddress = ExampleUser.generate().getMailAddress();
        final var mail = Mail.mailAddressAuthMail(this.siteURL, this.fromMailAddress, toMailAddress, UUID.randomUUID());
        this.sendMailService.execute(mail);
    }

    @Test
    public void testExecuteMailAddressEditMail() throws MessagingException {
        final var toMailAddress = ExampleUser.generate().getMailAddress();
        final var mail = Mail.mailAddressEditMail(this.siteURL, this.fromMailAddress, toMailAddress, UUID.randomUUID());
        this.sendMailService.execute(mail);
    }

    @Test
    public void testExecutePasswordEditedNoticeMail() throws MessagingException {
        final var toMailAddress = ExampleUser.generate().getMailAddress();
        final var mail = Mail.passwordEditedNoticeMail(this.siteURL, this.fromMailAddress, toMailAddress);
        this.sendMailService.execute(mail);
    }

    @Test
    public void testExecutePasswordResetMail() throws MessagingException {
        final var toMailAddress = ExampleUser.generate().getMailAddress();
        final var mail = Mail.passwordResetMail(this.siteURL, this.fromMailAddress, toMailAddress, UUID.randomUUID());
        this.sendMailService.execute(mail);
    }
}
