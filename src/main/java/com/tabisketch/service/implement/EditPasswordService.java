package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditPasswordForm;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.exception.UpdateFailedException;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditPasswordService;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditPasswordService implements IEditPasswordService {
    @Value("${SITE_URL}")
    private String siteURL;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    private final IUsersMapper usersMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    public EditPasswordService(
            final IUsersMapper usersMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder
    ) {
        this.usersMapper = usersMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void execute(final EditPasswordForm editPasswordForm) throws SelectFailedException, InvalidPasswordException, UpdateFailedException, MessagingException {
        // Userが存在しなければエラー
        final User user = this.usersMapper.selectByMailAddress(editPasswordForm.getMailAddress());
        if (user == null) throw new SelectFailedException(User.class.getName());

        // パスワードが一致していなければエラー
        final boolean isNotMatchPassword = !this.passwordEncoder.matches(editPasswordForm.getCurrentPassword(), user.getPassword());
        if (isNotMatchPassword) throw new InvalidPasswordException();

        // UserのPasswordを更新
        final String encryptedPassword = this.passwordEncoder.encode(editPasswordForm.getNewPassword());
        final int updateUserResult = this.usersMapper.updatePassword(user.getId(), encryptedPassword);
        if (updateUserResult != 1) throw new UpdateFailedException(User.class.getName());

        // パスワード編集通知メールを送信
        final var mail = Mail.passwordEditedNoticeMail(this.siteURL, this.fromMailAddress, user.getMailAddress());
        this.sendMailService.execute(mail);
    }
}
