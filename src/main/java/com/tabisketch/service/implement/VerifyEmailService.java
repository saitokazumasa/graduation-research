package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.exception.FailedDeleteException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.exception.FailedUpdateException;
import com.tabisketch.exception.InvalidEmailVerificationTokenException;
import com.tabisketch.mapper.IEmailVerificationTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IVerifyEmailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerifyEmailService implements IVerifyEmailService {
    private final IEmailVerificationTokensMapper emailVerificationTokensMapper;
    private final IUsersMapper usersMapper;

    public VerifyEmailService(final IEmailVerificationTokensMapper emailVerificationTokensMapper, final IUsersMapper usersMapper) {
        this.emailVerificationTokensMapper = emailVerificationTokensMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public void execute(final String uuid) {
        // 取得
        final var _uuid = UUID.fromString(uuid);
        final EmailVerificationToken evToken = this.emailVerificationTokensMapper.selectByUUID(_uuid);
        if (evToken == null) throw new FailedSelectException("failed to find email verification token");

        // トークンの有効期限を検証
        final var now = LocalDateTime.now();
        if (!evToken.getCreatedAt().equals(now) && evToken.getCreatedAt().isAfter(now))
            throw new InvalidEmailVerificationTokenException("email verification token is disabled");
        if (evToken.getCreatedAt().plusMinutes(30).isBefore(now))
            throw new InvalidEmailVerificationTokenException("email verification token is disabled");

        // 認証
        final boolean wasUpdatedUser = this.usersMapper.updateEmailVerified(evToken.getUserId(), true) == 1;
        if (!wasUpdatedUser) throw new FailedUpdateException("failed to update user");

        // トークン削除
        final boolean wasDeletedEVToken = this.emailVerificationTokensMapper.delete(_uuid) == 1;
        if (!wasDeletedEVToken) throw new FailedDeleteException("failed to delete email verification token");
    }
}
