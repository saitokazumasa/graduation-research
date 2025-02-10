package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.bean.form.ExampleCreateEmailVerificationTokenForm;
import com.tabisketch.mapper.IEmailVerificationTokenMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CreateEmailVerificationTokenServiceTest {
    @Autowired
    private ICreateEmailVerificationTokenService service;
    @MockitoBean
    private IEmailVerificationTokenMapper mapper;

    @Test
    public void testExecute() {
        final var emailVerificationToken = ExampleEmailVerificationToken.gen();
        when(this.mapper.insert(any())).thenReturn(1);
        when(this.mapper.selectByUUID(any())).thenReturn(emailVerificationToken);

        final var form = ExampleCreateEmailVerificationTokenForm.gen();
        this.service.execute(form);

        verify(this.mapper).insert(any());
        verify(this.mapper).selectByUUID(any());
    }
}
