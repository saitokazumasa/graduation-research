package com.tabisketch.service;

import com.tabisketch.bean.form.SendResetPasswordMailForm;
import jakarta.mail.MessagingException;

public interface ISendResetPasswordMailService {
    void execute(final SendResetPasswordMailForm form) throws MessagingException;
}
