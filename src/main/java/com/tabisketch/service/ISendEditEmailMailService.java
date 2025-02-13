package com.tabisketch.service;

import com.tabisketch.bean.form.EditEmailForm;
import jakarta.mail.MessagingException;

public interface ISendEditEmailMailService {
    void execute(final String currentEmail, final EditEmailForm form) throws MessagingException;
}
