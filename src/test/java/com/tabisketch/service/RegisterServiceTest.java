package com.tabisketch.service;

import com.tabisketch.bean.form.ExampleRegisterForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.mapper.IMAATokensMapper;
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
public class RegisterServiceTest {
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IMAATokensMapper maaTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @Autowired
    private IRegisterService registerService;

    @Test
    public void testExecute() throws InvalidMailAddressException, MessagingException, InsertFailedException {
        when(this.usersMapper.selectByMailAddress(anyString())).thenReturn(null);
        when(this.usersMapper.insert(any())).thenReturn(1);
        when(this.maaTokensMapper.insert(any())).thenReturn(1);

        final var registerForm = ExampleRegisterForm.generate();
        this.registerService.execute(registerForm);

        verify(this.usersMapper).selectByMailAddress(anyString());
        verify(this.usersMapper).insert(any());
        verify(this.maaTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
