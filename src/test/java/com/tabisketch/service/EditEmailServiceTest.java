package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleNewEmailVerificartionToken;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.NewEmailVerificationToken;
import com.tabisketch.exception.InvalidNewEmailVerificationTokenException;
import com.tabisketch.mapper.INewEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.implement.EditEmailService;
import jakarta.mail.MessagingException;
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
public class EditEmailServiceTest {
    @InjectMocks
    private EditEmailService editEmailService;
    @Mock
    private INewEmailVerificationTokensMapper newEmailVerificationTokensMapper;
    @Mock
    private IUsersMapper usersMapper;
    @Mock
    private ISendMailService sendMailService;

    @ParameterizedTest
    @MethodSource("provideTestData")
    public void testExecute(final NewEmailVerificationToken newEmailVerificationToken, final boolean isSuccess) throws MessagingException {
        if (isSuccess) {
            final var user = ExampleUser.gen();

            when(this.newEmailVerificationTokensMapper.selectByUUID(any())).thenReturn(newEmailVerificationToken);
            when(this.usersMapper.selectById(anyInt())).thenReturn(user);
            when(this.usersMapper.updateEmail(anyInt(), anyString())).thenReturn(1);
            when(this.newEmailVerificationTokensMapper.delete(any())).thenReturn(1);

            final var uuid = newEmailVerificationToken.getUuid().toString();
            this.editEmailService.execute(uuid);

            verify(this.newEmailVerificationTokensMapper).selectByUUID(any());
            verify(this.usersMapper).selectById(anyInt());
            verify(this.usersMapper).updateEmail(anyInt(), anyString());
            verify(this.newEmailVerificationTokensMapper).delete(any());
            verify(this.sendMailService).execute(any());
            return;
        }

        final var user = ExampleUser.gen();

        when(this.newEmailVerificationTokensMapper.selectByUUID(any())).thenReturn(newEmailVerificationToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);

        final var uuid = newEmailVerificationToken.getUuid().toString();
        assertThrows(InvalidNewEmailVerificationTokenException.class, () -> this.editEmailService.execute(uuid));
    }

    private static Stream<Arguments> provideTestData() {
        final var example = ExampleNewEmailVerificartionToken.gen();
        final var uuid = example.getUuid();
        final var newEmail = example.getNewEmail();
        final var userId = example.getUserId();
        final var createdAt = example.getCreatedAt();

        return Stream.of(
                Arguments.of(example, true),
                Arguments.of(new NewEmailVerificationToken(uuid, newEmail, userId, createdAt.minusMinutes(NewEmailVerificationToken.LIFETIME_MINUTES - 1)), true),
                Arguments.of(new NewEmailVerificationToken(uuid, newEmail, userId, createdAt.minusMinutes(NewEmailVerificationToken.LIFETIME_MINUTES)), false)
        );
    }
}
