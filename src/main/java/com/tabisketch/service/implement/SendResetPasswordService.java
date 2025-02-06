package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.PasswordResetToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendResetPasswordForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.mapper.IPasswordResetTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.service.ISendResetPasswordService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendResetPasswordService implements ISendResetPasswordService {
    @Value("${SITE_URL}")
    private String siteURL;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    private final IUsersMapper usersMapper;
    private final IPasswordResetTokensMapper passwordResetTokensMapper;
    private final ISendMailService sendMailService;

    public SendResetPasswordService(
            final IUsersMapper usersMapper,
            final IPasswordResetTokensMapper passwordResetTokensMapper,
            final ISendMailService sendMailService
    ) {
        this.usersMapper = usersMapper;
        this.passwordResetTokensMapper = passwordResetTokensMapper;
        this.sendMailService = sendMailService;
    }

    public void execute(final SendResetPasswordForm sendResetPasswordForm) throws InsertFailedException, MessagingException, SelectFailedException {
        // Userが存在しなければエラー
        final User user = this.usersMapper.selectByMailAddress(sendResetPasswordForm.getMailAddress());
        if (user == null) throw new SelectFailedException(User.class.getName());

        // PasswordResetTokenを追加
        final PasswordResetToken passwordResetToken = PasswordResetToken.generate(user.getId());
        final int insertPasswordResetTokenResult = this.passwordResetTokensMapper.insert(passwordResetToken);
        if (insertPasswordResetTokenResult != 1) throw new InsertFailedException(PasswordResetToken.class.getName());

        // パスワードリセットメールを送信
        final var mail = Mail.passwordResetMail(this.siteURL, this.fromMailAddress, user.getMailAddress(), passwordResetToken.getToken());
        this.sendMailService.execute(mail);
    }
}
