package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MAAToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IEncryptPasswordService;
import com.tabisketch.valueobject.Mail;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService implements IRegisterService {
    private final IUsersMapper usersMapper;
    private final IEncryptPasswordService encryptPasswordService;
    private final IMAATokensMapper maaTokensMapper;
    private final ISendMailService sendMailService;

    public RegisterService(
            final IUsersMapper usersMapper,
            final IEncryptPasswordService encryptPasswordService,
            final IMAATokensMapper maaTokensMapper,
            final ISendMailService sendMailService
    ) {
        this.usersMapper = usersMapper;
        this.encryptPasswordService = encryptPasswordService;
        this.maaTokensMapper = maaTokensMapper;
        this.sendMailService = sendMailService;
    }

    @Override
    @Transactional
    public void execute(final RegisterForm registerForm) throws MessagingException {
        if (registerForm.isNotMatchPasswordAndRePassword()) return;

        final String encryptedPassword = this.encryptPasswordService.execute(registerForm.getPassword());
        final var user = User.generate(registerForm.getMailAddress(), encryptedPassword);
        this.usersMapper.insert(user);

        final var maaToken = MAAToken.generate(user.getId());
        this.maaTokensMapper.insert(maaToken);

        final var mail = Mail.registrationMail(user.getMailAddress(), maaToken.getToken());
        this.sendMailService.execute(mail);
    }
}
