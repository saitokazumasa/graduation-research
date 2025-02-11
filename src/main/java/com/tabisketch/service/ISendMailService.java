package com.tabisketch.service;

import com.tabisketch.bean.form.SendMailForm;
import jakarta.mail.MessagingException;

public interface ISendMailService {
    void execute(final SendMailForm form) throws MessagingException;
}
