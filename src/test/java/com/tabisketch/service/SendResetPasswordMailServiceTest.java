package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleSendResetPasswordMailForm;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SendResetPasswordMailServiceTest {
    @Autowired
    private ISendResetPasswordMailService sendResetPasswordMailService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IResetPasswordTokensMapper resetPasswordTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;

    @Test
    public void testExecute() throws MessagingException {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.resetPasswordTokensMapper.insert(any())).thenReturn(1);

        final var form = ExampleSendResetPasswordMailForm.gen();
        this.sendResetPasswordMailService.execute(form);

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.resetPasswordTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
