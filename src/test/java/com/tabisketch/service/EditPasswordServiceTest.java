package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditPasswordForm;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.EditPasswordService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditPasswordServiceTest {
    @InjectMocks
    private EditPasswordService editPasswordService;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private ISendMailService sendMailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testExecute() throws MessagingException {
        final var user = ExampleUser.gen();
        when(this.usersMapper.selectByEmail(anyString())).thenReturn(user);
        when(this.passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(this.passwordEncoder.encode(anyString())).thenReturn("encrypted");
        when(this.usersMapper.updatePassword(anyInt(), anyString())).thenReturn(1);

        final var editPasswordForm = ExampleEditPasswordForm.gen();
        this.editPasswordService.execute(user.getEmail(), editPasswordForm);

        verify(this.usersMapper).selectByEmail(anyString());
        verify(this.passwordEncoder).matches(anyString(), anyString());
        verify(this.passwordEncoder).encode(anyString());
        verify(this.usersMapper).updatePassword(anyInt(), anyString());
        verify(this.sendMailService).execute(any());
    }
}
