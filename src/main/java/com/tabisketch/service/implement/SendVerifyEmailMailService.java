package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.bean.form.SendVerifyEmailMailFrom;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.service.ISendVerifyEmailMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SendVerifyEmailMailService implements ISendVerifyEmailMailService {
    private final IUsersMapper usersMapper;
    private final IEmailVerificationTokensMapper emailVerificationTokensMapper;
    private final ISendMailService sendMailService;
    private final String tabisketchEmail;

    public SendVerifyEmailMailService(
            final IUsersMapper usersMapper,
            final IEmailVerificationTokensMapper emailVerificationTokensMapper,
            final ISendMailService sendMailService,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.usersMapper = usersMapper;
        this.emailVerificationTokensMapper = emailVerificationTokensMapper;
        this.sendMailService = sendMailService;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final SendVerifyEmailMailFrom form) throws MessagingException {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(form.getEmail());
        if (user == null) throw new FailedSelectException("failed to find user");

        // 追加
        final var evToken = new EmailVerificationToken(UUID.randomUUID(), user.getId(), LocalDateTime.now());
        final boolean wasInsertEVToken = this.emailVerificationTokensMapper.insert(evToken) == 1;
        if (!wasInsertEVToken) throw new FailedInsertException("failed to insert email verification token");

        // 認証メールを送信
        final String uuid = evToken.getUuid().toString();
        final SendMailForm sendMailForm = SendMailForm.genRegisterMail(tabisketchEmail, user.getEmail(), uuid);
        this.sendMailService.execute(sendMailForm);
    }
}
