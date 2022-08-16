package com.studyLog4u.security.oauth2.oauth.impl;

import com.studyLog4u.security.oauth2.oauth.SocialOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}
