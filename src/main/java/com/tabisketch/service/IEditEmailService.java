package com.tabisketch.service;

import jakarta.mail.MessagingException;

public interface IEditEmailService {
    void execute(final String uuid) throws MessagingException;
}
