package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.form.CreateEmailVerificationTokenForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IEmailVerificationTokenMapper;
import com.tabisketch.service.ICreateEmailVerificationTokenService;
import org.springframework.stereotype.Service;

@Service
public class CreateEmailVerificationTokenService implements ICreateEmailVerificationTokenService {
    private final IEmailVerificationTokenMapper emailVerificationTokenMapper;

    public CreateEmailVerificationTokenService(final IEmailVerificationTokenMapper emailVerificationTokenMapper) {
        this.emailVerificationTokenMapper = emailVerificationTokenMapper;
    }

    @Override
    public EmailVerificationToken execute(final CreateEmailVerificationTokenForm form) {
        // 追加
        final var emailVerificationToken = form.toEmailVerificationToken();
        final int result = this.emailVerificationTokenMapper.insert(emailVerificationToken);
        if (result != 1) throw new FailedInsertException("failed insert email_verification_token");

        // 作成したものを取得
        final var created = this.emailVerificationTokenMapper.selectByUUID(emailVerificationToken.getUuid());
        if (created == null) throw new FailedSelectException("failed select email_verification_token");

        return created;
    }
}
