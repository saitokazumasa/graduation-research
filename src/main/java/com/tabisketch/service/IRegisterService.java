package com.tabisketch.service;

import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import jakarta.mail.MessagingException;

public interface IRegisterService {
    void execute(final RegisterForm registerForm) throws InvalidMailAddressException, InsertFailedException, MessagingException;
}
