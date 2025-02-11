package com.tabisketch.service.implement;

import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMailService implements ISendMailService {
    private final JavaMailSender mailSender;

    public SendMailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void execute(final SendMailForm form) throws MessagingException {
        // 作成
        final var message = this.mailSender.createMimeMessage();
        final var messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setFrom(form.getFrom());
        messageHelper.setTo(form.getTo());
        messageHelper.setSubject(form.getSubject());
        messageHelper.setText(form.getContent());

        // 送信
        this.mailSender.send(message);
    }
}
