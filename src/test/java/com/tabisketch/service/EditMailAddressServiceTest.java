package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.form.ExampleEditMailAddressForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EditMailAddressServiceTest {
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private IMAATokensMapper maaTokensMapper;
    @MockitoBean
    private ISendMailService sendMailService;
    @Autowired
    private IEditMailAddressService editMailAddressService;

    @Test
    @WithMockUser
    public void testExecute() throws InvalidMailAddressException, MessagingException, SelectFailedException, InvalidPasswordException, InsertFailedException {
        final var user = ExampleUser.generate();
        final var editMailAddressForm = ExampleEditMailAddressForm.generate();
        when(this.usersMapper.selectByMailAddress(editMailAddressForm.getCurrentMailAddress())).thenReturn(user);
        when(this.usersMapper.selectByMailAddress(editMailAddressForm.getNewMailAddress())).thenReturn(null);
        when(this.maaTokensMapper.insert(any())).thenReturn(1);

        this.editMailAddressService.execute(editMailAddressForm);

        verify(this.usersMapper, times(2)).selectByMailAddress(anyString());
        verify(this.maaTokensMapper).insert(any());
        verify(this.sendMailService).execute(any());
    }
}
