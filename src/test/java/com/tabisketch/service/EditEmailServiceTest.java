package com.tabisketch.service;

import com.tabisketch.bean.entity.ExampleNewEmailVerificartionToken;
import com.tabisketch.bean.entity.ExampleUser;
import com.tabisketch.bean.entity.NewEmailVerificationToken;
import com.tabisketch.mapper.INewEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import jakarta.mail.MessagingException;
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
public class EditEmailServiceTest {
    @Autowired
    private IEditEmailService editEmailService;
    @MockitoBean
    private INewEmailVerificationTokensMapper newEmailVerificationTokensMapper;
    @MockitoBean
    private IUsersMapper usersMapper;
    @MockitoBean
    private ISendMailService sendMailService;

    @Test
    public void testExecute() throws MessagingException {
        final var nevToken = ExampleNewEmailVerificartionToken.gen();
        final var user = ExampleUser.gen();

        when(this.newEmailVerificationTokensMapper.selectByUUID(any())).thenReturn(nevToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);
        when(this.usersMapper.updateEmail(anyInt(), anyString())).thenReturn(1);
        when(this.newEmailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = nevToken.getUuid().toString();
        this.editEmailService.execute(uuid);

        verify(this.newEmailVerificationTokensMapper).selectByUUID(any());
        verify(this.usersMapper).selectById(anyInt());
        verify(this.usersMapper).updateEmail(anyInt(), anyString());
        verify(this.newEmailVerificationTokensMapper).delete(any());
        verify(this.sendMailService).execute(any());
    }

    @ParameterizedTest
    @MethodSource("lifeTimeTestData")
    public void testLifeTime(final LifeTimeTestData testData) {
        final var user = ExampleUser.gen();

        when(this.newEmailVerificationTokensMapper.selectByUUID(any())).thenReturn(testData.resetPasswordToken);
        when(this.usersMapper.selectById(anyInt())).thenReturn(user);
        when(this.usersMapper.updateEmail(anyInt(), anyString())).thenReturn(1);
        when(this.newEmailVerificationTokensMapper.delete(any())).thenReturn(1);

        final var uuid = testData.resetPasswordToken.getUuid().toString();

        try {
            this.editEmailService.execute(uuid);
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            assert !testData.isSuccess;
            return;
        }
        assert testData.isSuccess;
    }

    public record LifeTimeTestData(NewEmailVerificationToken resetPasswordToken, boolean isSuccess) {
    }

    private static Stream<Object> lifeTimeTestData() {
        final var stream = Stream.builder();
        final var uuid = UUID.fromString("bd725533-53a3-4a2d-9289-7fcbc7250d82");
        final var createdAt = LocalDateTime.now().minusMinutes(NewEmailVerificationToken.LIFETIME_MINUTES);

        stream.add(new LifeTimeTestData(
                new NewEmailVerificationToken(uuid, "example@example.com", 1, createdAt.minusMinutes(1)), false));
        stream.add(new LifeTimeTestData(
                new NewEmailVerificationToken(uuid, "example@example.com", 1, createdAt.plusMinutes(1)), true));

        return stream.build();
    }
}
