package com.studyLog4u.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JwtTokenService, JwtFilter를 SecurityConfig에 적용할 때 사용할 JwtSecurityConfig 클래스
 */
@RequiredArgsConstructor
@Slf4j
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenService jwtTokenService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
//        super.configure(builder);
        JwtFilter customFilter = new JwtFilter(jwtTokenService);
        httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
