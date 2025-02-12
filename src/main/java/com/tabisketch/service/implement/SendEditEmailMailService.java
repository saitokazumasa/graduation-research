package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.NewEmailVerificationToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditEmailForm;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.InvalidEmailException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.mapper.INewEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendEditEmailMailService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SendEditEmailMailService implements ISendEditEmailMailService {
    private final IUsersMapper usersMapper;
    private final INewEmailVerificationTokensMapper newEmailVerificationTokensMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;
    private final String tabisketchEmail;

    public SendEditEmailMailService(
            final IUsersMapper usersMapper,
            final INewEmailVerificationTokensMapper newEmailVerificationTokensMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.usersMapper = usersMapper;
        this.newEmailVerificationTokensMapper = newEmailVerificationTokensMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final String currentEmail, final EditEmailForm form) throws MessagingException {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(currentEmail);
        if (user == null) throw new FailedSelectException("failed to find user");

        // パスワード一致の確認
        final boolean isMatchPassword = this.passwordEncoder.matches(form.getPassword(), user.getPassword());
        if (!isMatchPassword) throw new InvalidPasswordException("invalid password");

        // メールアドレスの存在確認
        final boolean isExistEmail = this.usersMapper.selectByEmail(form.getNewEmail()) != null;
        if (isExistEmail) throw new InvalidEmailException("invalid email");

        // トークン作成
        final var nevToken = new NewEmailVerificationToken(UUID.randomUUID(), form.getNewEmail(), user.getId(), LocalDateTime.now());
        final boolean wasInsertNEVToken = this.newEmailVerificationTokensMapper.insert(nevToken) == 1;
        if (!wasInsertNEVToken) throw new FailedInsertException("failed to insert new email verification token");

        // 認証メールを送信
        final String uuid = nevToken.getUuid().toString();
        final SendMailForm sendMailForm = SendMailForm.genEditEmailMail(tabisketchEmail, form.getNewEmail(), uuid);
        this.sendMailService.execute(sendMailForm);
    }
}
