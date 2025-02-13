package com.tabisketch.service;

import com.tabisketch.bean.form.ResetPasswordForm;
import jakarta.mail.MessagingException;

public interface IResetPasswordService {
    void execute(final String uuid, final ResetPasswordForm form) throws MessagingException;
}
