package com.tabisketch.service;

import com.tabisketch.bean.form.SendVerifyEmailMailFrom;
import jakarta.mail.MessagingException;

public interface ISendVerifyEmailMailService {
    void execute(final SendVerifyEmailMailFrom form) throws MessagingException;
}
