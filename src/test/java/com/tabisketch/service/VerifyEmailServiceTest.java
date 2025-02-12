package com.tabisketch.service;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("lifeTimeTestData")
    public void testExpiration(final LifeTimeTestData testData) {
        when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(testData.emailVerificationToken);
        when(this.usersMapper.updateEmailVerified(anyInt(), anyBoolean())).thenReturn(1);
        when(this.emailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = testData.emailVerificationToken.getUuid().toString();

        try {
            this.verifyEmailService.execute(uuid);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            assert !testData.isSuccess;
            return;
        }
        assert testData.isSuccess;
    }

    public record LifeTimeTestData(EmailVerificationToken emailVerificationToken, boolean isSuccess) {
    }

    private static Stream<Object> lifeTimeTestData() {
        final var stream = Stream.builder();
        final var uuid = UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82");
        final var createdAt = LocalDateTime.now().minusMinutes(EmailVerificationToken.LIFETIME_MINUTES);

        stream.add(new LifeTimeTestData(
                new EmailVerificationToken(uuid, 1, createdAt.minusMinutes(1)), false));
        stream.add(new LifeTimeTestData(
                new EmailVerificationToken(uuid, 1, createdAt.plusMinutes(1)), true));

        return stream.build();
    }
}
