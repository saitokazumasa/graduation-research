package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VerifyEmailServiceTest {
    @Autowired
    private IVerifyEmailService verifyEmailService;
    @MockitoBean
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @MockitoBean
    private IUsersMapper usersMapper;

    @Test
    public void testExecute() {
        final var evToken = ExampleEmailVerificationToken.gen();
        when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(evToken);
        when(this.usersMapper.updateEmailVerified(anyInt(), anyBoolean())).thenReturn(1);
        when(this.emailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = evToken.getUuid().toString();
        this.verifyEmailService.execute(uuid);

        verify(this.emailVerificationTokensMapper).selectByUUID(any());
        verify(this.usersMapper).updateEmailVerified(anyInt(), anyBoolean());
        verify(this.emailVerificationTokensMapper).delete(any());
    }
}
