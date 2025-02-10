package com.tabisketch.service;

import com.tabisketch.bean.form.ExampleSendMailForm;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailServiceTest {
    @Autowired
    private ISendMailService service;
    @Value("${spring.mail.username}")
    private String tabisketchEmail;

    @Test
    public void testExecute() throws MessagingException {
        final var form = ExampleSendMailForm.gen(tabisketchEmail, tabisketchEmail);
        // NOTE: メール送信テストをしたい場合は、以下のコメントアウトを解除して実行する
//        this.service.execute(form);
    }
}
