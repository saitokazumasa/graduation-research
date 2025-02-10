package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleResetPasswordToken;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleResetPasswordForm;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ResetPasswordServiceTest {
    @Autowired
    private IResetPasswordService resetPasswordService;
    @MockitoBean
    private IResetPasswordTokensMapper resetPasswordTokensMapper;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        final var rpToken = ExampleResetPasswordToken.gen();
        final var user = ExampleUser.gen();

        when(this.resetPasswordTokensMapper.selectByUuid(any())).thenReturn(rpToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);
        when(this.passwordEncoder.encode(anyString())).thenReturn("encrypted");
        when(this.usersMapper.updatePassword(anyInt(), anyString())).thenReturn(1);
        when(this.resetPasswordTokensMapper.delete(any())).thenReturn(1);

        final var uuid = rpToken.getUuid().toString();
        final var form = ExampleResetPasswordForm.gen();
        this.resetPasswordService.execute(uuid, form);

        verify(this.resetPasswordTokensMapper).selectByUuid(any());
        verify(this.usersMapper).selectById(anyInt());
        verify(this.passwordEncoder).encode(anyString());
        verify(this.usersMapper).updatePassword(anyInt(), anyString());
        verify(this.resetPasswordTokensMapper).delete(any());
        verify(this.sendMailService).execute(any());
    }
}
