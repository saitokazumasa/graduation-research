package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleSendResetPasswordMailForm;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.SendResetPasswordMailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendResetPasswordMailServiceTest {
    @InjectMocks
    private SendResetPasswordMailService sendResetPasswordMailService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private IResetPasswordTokensMapper resetPasswordTokensMapper;
    @Mock
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
