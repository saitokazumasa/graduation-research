package com.tabisketch.service.implement;

import com.tabisketch.valueobject.Mail;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailService implements ISendMailService {
    private final JavaMailSender mailSender;

    public SendMailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void execute(final Mail mail) throws MessagingException {
        // メールを作成
        final var message = this.mailSender.createMimeMessage();
        final var messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(mail.getFromMailAddress());
        messageHelper.setTo(mail.getToMailAddress());
        messageHelper.setSubject(mail.getSubject());
        messageHelper.setText(mail.getContent());

        // 送信
        this.mailSender.send(message);
    }
}
