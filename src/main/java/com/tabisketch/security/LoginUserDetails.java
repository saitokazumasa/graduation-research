package com.tabisketch.security;

import com.tabisketch.bean.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUserDetails implements UserDetails {
    private final User user;

    public LoginUserDetails(final User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 現状はユーザーによって権限が異ならないため適当に "USER" を入れている
        return AuthorityUtils.createAuthorityList("ROLE_" + "USER");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // NOTE: メールアドレスを "username" として扱う
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // メールアドレスが認証されているかどうかで有効無効を分ける
        return user.isEmailVerified();
    }
}
