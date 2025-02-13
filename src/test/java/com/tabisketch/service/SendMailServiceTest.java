package com.tabisketch.service;

import com.tabisketch.bean.form.ExampleSendMailForm;
import com.tabisketch.service.implement.SendMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
public class SendMailServiceTest {
    @InjectMocks
    private SendMailService sendMailService;
    @Value("${spring.mail.username}")
    private String tabisketchEmail;

    @Test
    public void testExecute() throws MessagingException {
        final var form = ExampleSendMailForm.gen(tabisketchEmail, tabisketchEmail);
        // NOTE: メール送信テストをしたい場合は、以下のコメントアウトを解除して実行する
//        this.sendMailService.execute(form);
    }
}
