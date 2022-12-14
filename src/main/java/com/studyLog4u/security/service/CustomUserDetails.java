package com.studyLog4u.security.service;

import com.studyLog4u.entity.Member;
import com.studyLog4u.oauth.helper.constants.RoleType;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final String password;
    private final SocialLoginType socialLoginType;
    private final Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return userId; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
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
        return true;
    }

    public static CustomUserDetails create(Member member){
        return new CustomUserDetails(
                member.getUserId(),
                member.getPassword(),
                member.getSocialType(),
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );
    }
}
