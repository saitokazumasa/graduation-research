package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleSendVerifyEmailMailForm;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.SendVerifyEmailMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendVerifyEmailMailServiceTest {
    @InjectMocks
    private SendVerifyEmailMailService sendVerifyEmailMailService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @Mock
    private ISendMailService sendMailService;

    @Test
    public void testExecute() throws MessagingException {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(any())).thenReturn(user);
        when(this.emailVerificationTokensMapper.insert(any())).thenReturn(1);

        final var form = ExampleSendVerifyEmailMailForm.gen();
        this.sendVerifyEmailMailService.execute(form);

        verify(this.usersMapper).selectByEmail(any());
        verify(this.emailVerificationTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
