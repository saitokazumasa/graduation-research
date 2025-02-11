package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditEmailForm;
import com.tabisketch.mapper.INewEmailVerificationTokensMapper;
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
public class SendEdtiEmailMailServiceTest {
    @Autowired
    private ISendEditEmailMailService sendEditEmailMailService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private INewEmailVerificationTokensMapper newEmailVerificationTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(this.newEmailVerificationTokensMapper.insert(any())).thenReturn(1);

        final var form = ExampleEditEmailForm.gen();
        this.sendEditEmailMailService.execute(user.getEmail(), form);

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.passwordEncoder).matches(anyString(), anyString());
        verify(this.newEmailVerificationTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
