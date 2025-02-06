package com.tabisketch.service;

import com.tabisketch.bean.form.EditPasswordForm;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import com.tabisketch.exception.UpdateFailedException;
import jakarta.mail.MessagingException;

public interface IEditPasswordService {
    void execute(final EditPasswordForm editPasswordForm) throws SelectFailedException, InvalidPasswordException, UpdateFailedException, MessagingException;
}
