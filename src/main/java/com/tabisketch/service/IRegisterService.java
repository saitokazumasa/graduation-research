package com.tabisketch.service;

import com.tabisketch.bean.form.RegisterForm;
import jakarta.mail.MessagingException;

public interface IRegisterService {
    void execute(final RegisterForm form) throws MessagingException;
}
