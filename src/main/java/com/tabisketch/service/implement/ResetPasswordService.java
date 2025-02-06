package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.PasswordResetToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.ResetPasswordForm;
import com.tabisketch.exception.DeleteFailedException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IPasswordResetTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IResetPasswordService;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class ResetPasswordService implements IResetPasswordService {
    @Value("${SITE_URL}")
    private String siteURL;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    private final IPasswordResetTokensMapper passwordResetTokensMapper;
    private final IUsersMapper usersMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    public ResetPasswordService(
            final IPasswordResetTokensMapper passwordResetTokensMapper,
            final IUsersMapper usersMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder
    ) {
        this.passwordResetTokensMapper = passwordResetTokensMapper;
        this.usersMapper = usersMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void execute(final ResetPasswordForm resetPasswordForm) throws SelectFailedException, UpdateFailedException, DeleteFailedException, MessagingException {
        // PasswordResetTokenが存在しなければエラー
        final var token = UUID.fromString(resetPasswordForm.getToken());
        final PasswordResetToken passwordResetToken = this.passwordResetTokensMapper.selectByToken(token);
        if (passwordResetToken == null) throw new SelectFailedException(PasswordResetToken.class.getName());

        // TODO: PasswordResetTokenの有効期限が切れていたらエラー

        // Userが存在しなければエラー
        final User user = this.usersMapper.selectById(passwordResetToken.getUserId());
        if (user == null) throw new SelectFailedException(User.class.getName());

        // Userのpasswordを更新
        final String encryptedPassword = this.passwordEncoder.encode(resetPasswordForm.getPassword());
        final int updateUserResult = this.usersMapper.updatePassword(user.getId(), encryptedPassword);
        if (updateUserResult != 1) throw new UpdateFailedException(User.class.getName());

        // PasswordResetTokenを削除
        final int deletePasswordResetTokenResult = this.passwordResetTokensMapper.deleteById(passwordResetToken.getId());
        if (deletePasswordResetTokenResult != 1) throw new DeleteFailedException(PasswordResetToken.class.getName());

        // パスワード編集通知メールを送信
        final var mail = Mail.passwordEditedNoticeMail(this.siteURL, this.fromMailAddress, user.getMailAddress());
        this.sendMailService.execute(mail);
    }
}
