package com.tabisketch.service;

import com.tabisketch.bean.form.EditPasswordForm;
import jakarta.mail.MessagingException;

public interface IEditPasswordService {
    void execute(final String email, final EditPasswordForm form) throws MessagingException;
}
