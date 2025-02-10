package com.tabisketch.service;

import com.tabisketch.bean.entity.EmailVerificationToken;
import com.tabisketch.bean.form.CreateEmailVerificationTokenForm;

public interface ICreateEmailVerificationTokenService {
    EmailVerificationToken execute(final CreateEmailVerificationTokenForm form);
}
