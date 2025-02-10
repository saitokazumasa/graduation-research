package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.CreateUserForm;
import com.tabisketch.exception.FailedInsertException;
import com.tabisketch.exception.FailedSelectException;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.ICreateUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserService implements ICreateUserService {
    private final IUsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(final IUsersMapper usersMapper, final PasswordEncoder passwordEncoder) {
        this.usersMapper = usersMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User execute(final CreateUserForm form) {
        // パスワードをハッシュ化
        final var user = form.toUser();
        final var encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 追加
        final int result = this.usersMapper.insert(user);
        if (result != 1) throw new FailedInsertException("failed insert user");

        // 作成したものを取得
        final var created = this.usersMapper.selectById(user.getId());
        if (created == null) throw new FailedSelectException("failed select user");

        return created;
    }
}
