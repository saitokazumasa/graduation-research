package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditPasswordForm;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditPasswordService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditPasswordService implements IEditPasswordService {
    private final IUsersMapper usersMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;
    private final String tabisketchEmail;

    public EditPasswordService(
            final IUsersMapper usersMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.usersMapper = usersMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    @Transactional
    public void execute(final String email, final EditPasswordForm form) throws MessagingException {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(email);
        if (user == null) throw new FailedSelectException("failed to find user");

        // パスワード一致の確認
        final boolean isMatchPassword = this.passwordEncoder.matches(form.getCurrentPassword(), user.getPassword());
        if (!isMatchPassword) throw new InvalidPasswordException("invalid password");

        // パスワード更新
        final boolean wasUpdatedUser = this.usersMapper.updatePassword(user.getId(), form.getNewPassword()) == 1;
        if (!wasUpdatedUser) throw new FailedUpdateException("failed to update user");

        // 編集通知メールを送信
        final SendMailForm sendMailForm = SendMailForm.genComplateEditEmailMail(tabisketchEmail, user.getEmail());
        this.sendMailService.execute(sendMailForm);
    }
}
