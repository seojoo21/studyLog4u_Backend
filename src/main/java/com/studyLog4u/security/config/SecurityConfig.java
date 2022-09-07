package com.studyLog4u.security.config;

import com.studyLog4u.jwt.JwtSecurityConfig;
import com.studyLog4u.jwt.JwtTokenService;
import com.studyLog4u.jwt.handler.JwtAccessDeniedHandler;
import com.studyLog4u.jwt.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션을 메소드 단위로 추가한다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenService jwtTokenService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CORS 허용 & JWT 토큰을 사용하므로 csrf disabled
                .cors().and()
                .csrf().disable()
                // 예외처리 핸들링
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 세션을 사용하지 않으므로 stateless로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 권한 설정
                .and()
                .authorizeRequests()
                .antMatchers("/studyLog4uAPI.html", "/swagger-ui/**", "/api-docs/**", "/favicon.ico").permitAll()
                .antMatchers("/api/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(jwtTokenService));
    }
}
