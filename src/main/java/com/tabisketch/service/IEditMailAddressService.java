package com.tabisketch.service;

import com.tabisketch.bean.form.EditMailAddressForm;
import com.tabisketch.exception.InsertFailedException;
import com.tabisketch.exception.InvalidMailAddressException;
import com.tabisketch.exception.InvalidPasswordException;
import com.tabisketch.exception.SelectFailedException;
import jakarta.mail.MessagingException;

public interface IEditMailAddressService {
    void execute(final EditMailAddressForm editMailAddressForm) throws SelectFailedException, InvalidMailAddressException, InvalidPasswordException, InsertFailedException, MessagingException;
}
