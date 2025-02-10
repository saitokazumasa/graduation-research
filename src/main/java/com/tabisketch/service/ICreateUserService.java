package com.tabisketch.service;

import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.CreateUserForm;

public interface ICreateUserService {
    User execute(final CreateUserForm form);
}
