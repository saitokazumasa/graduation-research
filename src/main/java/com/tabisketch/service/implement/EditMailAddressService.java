package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MAAToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.mapper.IMAATokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IEditMailAddressService;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.valueobject.Mail;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditMailAddressService implements IEditMailAddressService {
    @Value("${SITE_URL}")
    private String siteURL;
    @Value("${spring.mail.username}")
    private String fromMailAddress;

    private final IUsersMapper usersMapper;
    private final IMAATokensMapper maaTokensMapper;
    private final ISendMailService sendMailService;
    private final PasswordEncoder passwordEncoder;

    public EditMailAddressService(
            final IUsersMapper usersMapper,
            final IMAATokensMapper maaTokensMapper,
            final ISendMailService sendMailService,
            final PasswordEncoder passwordEncoder
    ) {
        this.usersMapper = usersMapper;
        this.maaTokensMapper = maaTokensMapper;
        this.sendMailService = sendMailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void execute(final EditMailAddressForm editMailAddressForm) throws SelectFailedException, InvalidMailAddressException, InvalidPasswordException, InsertFailedException, MessagingException {
        // Userが存在しなければエラー
        final User user = this.usersMapper.selectByMailAddress(editMailAddressForm.getCurrentMailAddress());
        if (user == null) throw new SelectFailedException(User.class.getName());

        // 新しいメールアドレスが使われていればエラー
        final boolean isUsedMailAddress = this.usersMapper.selectByMailAddress(editMailAddressForm.getNewMailAddress()) != null;
        if (isUsedMailAddress) throw new InvalidMailAddressException();

        // パスワードが一致していなければエラー
        final boolean isNotMatchPassword = !this.passwordEncoder.matches(editMailAddressForm.getPassword(), user.getPassword());
        if (isNotMatchPassword) throw new InvalidPasswordException();

        // MAATokenを追加
        final var maaToken = editMailAddressForm.toMAAToken(user.getId());
        final int insertMAATokenResult = this.maaTokensMapper.insert(maaToken);
        if (insertMAATokenResult != 1) throw new InsertFailedException(MAAToken.class.getName());

        // メールアドレス認証メールを送信
        final var mail = Mail.mailAddressEditMail(this.siteURL, this.fromMailAddress, maaToken.getNewMailAddress(), maaToken.getToken());
        this.sendMailService.execute(mail);
    }
}
