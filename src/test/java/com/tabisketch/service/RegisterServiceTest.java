package com.tabisketch.service;

import com.tabisketch.bean.form.ExampleRegisterForm;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterServiceTest {
    @Autowired
    private IRegisterService service;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        when(this.passwordEncoder.encode(anyString())).thenReturn("encrypted");
        when(this.usersMapper.insert(any())).thenReturn(1);
        when(this.emailVerificationTokensMapper.insert(any())).thenReturn(1);

        final var form = ExampleRegisterForm.gen();
        this.service.execute(form);

        verify(this.passwordEncoder).encode(anyString());
        verify(this.usersMapper).insert(any());
        verify(this.emailVerificationTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
