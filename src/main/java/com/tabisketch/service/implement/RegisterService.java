package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegisterService implements IRegisterService {
    private final IUsersMapper usersMapper;
    private final IEmailVerificationTokensMapper emailVerificationTokensMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;
    private final String tabisketchEmail;

    public RegisterService(
            final IUsersMapper usersMapper,
            final IEmailVerificationTokensMapper emailVerificationTokensMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.usersMapper = usersMapper;
        this.emailVerificationTokensMapper = emailVerificationTokensMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final RegisterForm form) throws MessagingException {
        // パスワードをハッシュ化
        final String encryptedPassword = this.passwordEncoder.encode(form.getPassword());
        final var user = new User(-1, form.getEmail(), encryptedPassword, false);

        // 追加
        final boolean wasInsertUser = this.usersMapper.insert(user) == 1;
        if (!wasInsertUser) throw new FailedInsertException("failed to insert user");

        final var evToken = new EmailVerificationToken(UUID.randomUUID(), user.getId(), LocalDateTime.now());
        final boolean wasInsertEVToken = this.emailVerificationTokensMapper.insert(evToken) == 1;
        if (!wasInsertEVToken) throw new FailedInsertException("failed to insert email verification token");

        // 認証メールを送信
        final String uuid = evToken.getUuid().toString();
        final SendMailForm sendMailForm = SendMailForm.genCreateUserMail(tabisketchEmail, user.getEmail(), uuid);
        this.sendMailService.execute(sendMailForm);
    }
}
