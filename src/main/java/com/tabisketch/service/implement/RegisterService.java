package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.bean.form.SendVerifyEmailMailFrom;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendVerifyEmailMailService;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService implements IRegisterService {
    private final IUsersMapper usersMapper;
    private final ISendVerifyEmailMailService sendVerifyEmailMailService;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(
            final IUsersMapper usersMapper,
            final ISendVerifyEmailMailService sendVerifyEmailMailService,
            final PasswordEncoder passwordEncoder
    ) {
        this.usersMapper = usersMapper;
        this.sendVerifyEmailMailService = sendVerifyEmailMailService;
        this.passwordEncoder = passwordEncoder;
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

        // 認証メール送信
        final var sendVerifyEmailMailForm = new SendVerifyEmailMailFrom(form.getEmail());
        this.sendVerifyEmailMailService.execute(sendVerifyEmailMailForm);
    }
}
