package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditPasswordForm;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EditPasswordServiceTest {
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @Autowired
    private IEditPasswordService editPasswordService;

    @Test
    @WithMockUser
    public void testExecute() throws MessagingException, SelectFailedException, InvalidPasswordException, UpdateFailedException {
        final var user = ExampleUser.generate();
        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(user);
        when(this.usersMapper.updatePassword(anyInt(), anyString())).thenReturn(1);

        final var editPasswordForm = ExampleEditPasswordForm.generate();
        this.editPasswordService.execute(editPasswordForm);

        verify(this.usersMapper).selectByMailAddress(anyString());
        verify(this.usersMapper).updatePassword(anyInt(), anyString());
        verify(this.sendMailService).execute(any());
    }
}
