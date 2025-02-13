package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleResetPasswordToken;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.ResetPasswordToken;
import com.tabisketch.bean.form.ExampleResetPasswordForm;
import com.tabisketch.exception.InvalidResetPasswordTokenException;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.ResetPasswordService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordServiceTest {
    @InjectMocks
    private ResetPasswordService resetPasswordService;
    @Mock
    private IResetPasswordTokensMapper resetPasswordTokensMapper;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private ISendMailService sendMailService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testExecute(final ResetPasswordToken resetPasswordToken, final boolean isSuccess) throws MessagingException {
        if (isSuccess) {
            final var user = ExampleUser.gen();

            when(this.resetPasswordTokensMapper.selectByUUID(any())).thenReturn(resetPasswordToken);
            when(this.usersMapper.selectById(anyInt())).thenReturn(user);
            when(this.passwordEncoder.encode(anyString())).thenReturn("encrypted");
            when(this.usersMapper.updatePassword(anyInt(), anyString())).thenReturn(1);
            when(this.resetPasswordTokensMapper.delete(any())).thenReturn(1);

            final var uuid = resetPasswordToken.getUuid().toString();
            final var form = ExampleResetPasswordForm.gen();
            this.resetPasswordService.execute(uuid, form);

            verify(this.resetPasswordTokensMapper).selectByUUID(any());
            verify(this.usersMapper).selectById(anyInt());
            verify(this.passwordEncoder).encode(anyString());
            verify(this.usersMapper).updatePassword(anyInt(), anyString());
            verify(this.resetPasswordTokensMapper).delete(any());
            verify(this.sendMailService).execute(any());
            return;
        }

        final var user = ExampleUser.gen();

        when(this.resetPasswordTokensMapper.selectByUUID(any())).thenReturn(resetPasswordToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);

        final var uuid = resetPasswordToken.getUuid().toString();
        final var form = ExampleResetPasswordForm.gen();
        assertThrows(InvalidResetPasswordTokenException.class, () -> this.resetPasswordService.execute(uuid, form));
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleResetPasswordToken.gen();
        final var uuid = example.getUuid();
        final var userId = example.getUserId();
        final var createdAt = example.getCreatedAt();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new ResetPasswordToken(uuid, userId, createdAt.minusMinutes(ResetPasswordToken.LIFETIME_MINUTES - 1)), true),
                Arguments.of(new ResetPasswordToken(uuid, userId, createdAt.minusMinutes(ResetPasswordToken.LIFETIME_MINUTES)), false)
        );
    }
}
