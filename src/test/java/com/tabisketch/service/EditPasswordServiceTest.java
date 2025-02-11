package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditPasswordForm;
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
public class EditPasswordServiceTest {
    @Autowired
    private IEditPasswordService editPasswordService;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(this.usersMapper.updatePassword(anyInt(), anyString())).thenReturn(1);

        final var editPasswordForm = ExampleEditPasswordForm.gen();
        this.editPasswordService.execute(user.getEmail(), editPasswordForm);

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.passwordEncoder).matches(anyString(), anyString());
        verify(this.usersMapper).updatePassword(anyInt(), anyString());
        verify(this.sendMailService).execute(any());
    }
}
