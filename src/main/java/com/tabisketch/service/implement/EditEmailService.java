package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.NewEmailVerificationToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.exception.InvalidNewEmailVerificationTokenException;
import com.tabisketch.mapper.INewEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditEmailService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EditEmailService implements IEditEmailService {
    private final INewEmailVerificationTokensMapper newEmailVerificationTokensMapper;
    private final IUsersMapper usersMapper;
    private final ISendMailService sendMailService;
    private final String tabisketchEmail;

    public EditEmailService(
            final INewEmailVerificationTokensMapper newEmailVerificationTokensMapper,
            final IUsersMapper usersMapper,
            final ISendMailService sendMailService,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.newEmailVerificationTokensMapper = newEmailVerificationTokensMapper;
        this.usersMapper = usersMapper;
        this.sendMailService = sendMailService;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final String uuid) throws MessagingException {
        // トークン取得
        final var _uuid = UUID.fromString(uuid);
        final NewEmailVerificationToken nevToken = this.newEmailVerificationTokensMapper.selectByUUID(_uuid);
        if (nevToken == null) throw new FailedSelectException("failed to find email verification token");

        // ユーザー取得
        final User user = this.usersMapper.selectById(nevToken.getUserId());
        if (user == null) throw new FailedSelectException("failed to find user");

        // 有効期限を検証
        final var now = LocalDateTime.now();
        if (!nevToken.getLifeTime().equals(now) && nevToken.getLifeTime().isBefore(now))
            throw new InvalidNewEmailVerificationTokenException("new email verification token is disabled");

        // メールアドレス更新
        final boolean wasUpdatedUser = this.usersMapper.updateEmail(user.getId(), nevToken.getNewEmail()) == 1;
        if (!wasUpdatedUser) throw new FailedUpdateException("failed to update user");

        // トークン削除
        final boolean wasDeletedNEVToken = this.newEmailVerificationTokensMapper.delete(_uuid) == 1;
        if (!wasDeletedNEVToken) throw new FailedDeleteException("failed to delete new email verification token");

        // 編集通知メールを送信
        final SendMailForm sendMailForm = SendMailForm.genComplateEditEmailMail(tabisketchEmail, user.getEmail(), nevToken.getNewEmail());
        this.sendMailService.execute(sendMailForm);
    }
}
