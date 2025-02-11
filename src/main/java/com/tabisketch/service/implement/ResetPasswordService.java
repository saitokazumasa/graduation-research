package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.ResetPasswordToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.ResetPasswordForm;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.exception.InvalidResetPasswordTokenException;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IResetPasswordService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResetPasswordService implements IResetPasswordService {
    private final IResetPasswordTokensMapper resetPasswordTokensMapper;
    private final IUsersMapper usersMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;
    private final String tabisketchEmail;

    public ResetPasswordService(
            final IResetPasswordTokensMapper resetPasswordTokensMapper,
            final IUsersMapper usersMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.resetPasswordTokensMapper = resetPasswordTokensMapper;
        this.usersMapper = usersMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final String uuid, final ResetPasswordForm form) throws MessagingException {
        // トークン取得
        final var _uuid = UUID.fromString(uuid);
        final ResetPasswordToken rpToken = this.resetPasswordTokensMapper.selectByUuid(_uuid);
        if (rpToken == null) throw new FailedSelectException("failed to find reset password token");

        // ユーザー取得
        final User user = this.usersMapper.selectById(rpToken.getUserId());
        if (user == null) throw new FailedSelectException("failed to find user");

        // 有効期限を検証
        final var now = LocalDateTime.now();
        if (!rpToken.getLifeTime().equals(now) && rpToken.getLifeTime().isBefore(now))
            throw new InvalidResetPasswordTokenException("password reset token is disabled");

        // パスワード更新
        final String encryptedPassword = this.passwordEncoder.encode(form.getPassword());
        final boolean wasUpdatedUser = this.usersMapper.updatePassword(user.getId(), encryptedPassword) == 1;
        if (!wasUpdatedUser) throw new FailedUpdateException("failed to update user");

        // トークン削除
        final boolean wasDeletedRPToken = this.resetPasswordTokensMapper.delete(_uuid) == 1;
        if (!wasDeletedRPToken) throw new FailedDeleteException("failed to delete token");

        // 編集通知メールを送信
        final SendMailForm sendMailForm = SendMailForm.genCompleteResetPasswordMail(tabisketchEmail, user.getEmail());
        this.sendMailService.execute(sendMailForm);
    }
}
