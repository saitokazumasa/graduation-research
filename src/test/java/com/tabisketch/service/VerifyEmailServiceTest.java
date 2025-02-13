package com.tabisketch.service;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.entity.ExampleEmailVerificationToken;
import com.tabisketch.exception.InvalidEmailVerificationTokenException;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.VerifyEmailService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyEmailServiceTest {
    @InjectMocks
    private VerifyEmailService verifyEmailService;
    @Mock
    private IEmailVerificationTokensMapper emailVerificationTokensMapper;
    @Mock
    private IUsersMapper usersMapper;

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testExecute(final EmailVerificationToken emailVerificationToken, final boolean isSuccess) {
        if (isSuccess) {
            when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(emailVerificationToken);
            when(this.usersMapper.updateEmailVerified(anyInt(), anyBoolean())).thenReturn(1);
            when(this.emailVerificationTokensMapper.delete(any())).thenReturn(1);

            final var uuid = emailVerificationToken.getUuid().toString();
            this.verifyEmailService.execute(uuid);

            verify(this.emailVerificationTokensMapper).selectByUUID(any());
            verify(this.usersMapper).updateEmailVerified(anyInt(), anyBoolean());
            verify(this.emailVerificationTokensMapper).delete(any());
            return;
        }

        when(this.emailVerificationTokensMapper.selectByUUID(any())).thenReturn(emailVerificationToken);

        final var uuid = emailVerificationToken.getUuid().toString();
        assertThrows(InvalidEmailVerificationTokenException.class, () -> this.verifyEmailService.execute(uuid));
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleEmailVerificationToken.gen();
        final var uuid = example.getUuid();
        final var userId = example.getUserId();
        final var createdAt = example.getCreatedAt();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new EmailVerificationToken(uuid, userId, createdAt.minusMinutes(EmailVerificationToken.LIFETIME_MINUTES - 1)), true),
                Arguments.of(new EmailVerificationToken(uuid, userId, createdAt.minusMinutes(EmailVerificationToken.LIFETIME_MINUTES)), false)
        );
    }
}
