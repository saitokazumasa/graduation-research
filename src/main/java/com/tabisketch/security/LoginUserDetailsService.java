package com.tabisketch.security;

import com.tabisketch.bean.entity.User;
import com.tabisketch.mapper.IUsersMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    private final IUsersMapper usersMapper;

    public LoginUserDetailsService(final IUsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final var user = usersMapper.selectByEmail(email);

        if (user == null) throw new UsernameNotFoundException(email);

        return new LoginUserDetails(user);
    }
}
