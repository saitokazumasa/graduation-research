package com.tabisketch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/", "/top").permitAll()
                        .anyRequest().denyAll()
                ).formLogin(a -> a
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        // TODO: ログイン成功後に遷移するページURLに書き換える
                        .defaultSuccessUrl("/top")
                        .failureUrl("/login?error")
                        // NOTE: メールアドレスを "username" として扱う
                        .usernameParameter("mail")
                        .permitAll()
                ).logout(a -> a
                        .logoutSuccessUrl("/login")
                        .permitAll()
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
