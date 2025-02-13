package com.tabisketch.service;

import com.tabisketch.bean.form.ExampleRegisterForm;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.RegisterService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {
    @InjectMocks
    private RegisterService registerService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @Mock
    private ISendMailService sendMailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        when(this.passwordEncoder.encode(anyString())).thenReturn("encrypted");
        when(this.usersMapper.insert(any())).thenReturn(1);
        when(this.emailVerificationTokensMapper.insert(any())).thenReturn(1);

        final var form = ExampleRegisterForm.gen();
        this.registerService.execute(form);

        verify(this.passwordEncoder).encode(anyString());
        verify(this.usersMapper).insert(any());
        verify(this.emailVerificationTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
