package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.ResetPasswordToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.SendMailForm;
import com.tabisketch.bean.form.SendResetPasswordMailForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IResetPasswordTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ISendMailService;
import com.tabisketch.service.ISendResetPasswordMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SendResetPasswordMailService implements ISendResetPasswordMailService {
    private final IUsersMapper usersMapper;
    private final IResetPasswordTokensMapper resetPasswordTokensMapper;
    private final ISendMailService sendMailService;
    private final String tabisketchEmail;

    public SendResetPasswordMailService(
            final IUsersMapper usersMapper,
            final IResetPasswordTokensMapper resetPasswordTokensMapper,
            final ISendMailService sendMailService,
            final @Value("${spring.mail.username}") String tabisketchEmail
    ) {
        this.usersMapper = usersMapper;
        this.resetPasswordTokensMapper = resetPasswordTokensMapper;
        this.sendMailService = sendMailService;
        this.tabisketchEmail = tabisketchEmail;
    }

    @Override
    public void execute(final SendResetPasswordMailForm form) throws MessagingException {
        // ユーザー取得
        final User user = this.usersMapper.selectByEmail(form.getEmail());
        if (user == null) throw new FailedSelectException("failed to find user");

        // トークン作成
        final var rpToken = new ResetPasswordToken(UUID.randomUUID(), user.getId(), LocalDateTime.now());
        final boolean wasInsertRPToken = this.resetPasswordTokensMapper.insert(rpToken) == 1;
        if (!wasInsertRPToken) throw new FailedInsertException("failed to insert reset password token");

        // パスワードリセットメールを送信
        final String uuid = rpToken.getUuid().toString();
        final SendMailForm sendMailForm = SendMailForm.genResetPasswordMail(user.getEmail(), tabisketchEmail, uuid);
        this.sendMailService.execute(sendMailForm);
    }
}
