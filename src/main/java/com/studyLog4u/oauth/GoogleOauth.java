package com.studyLog4u.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth{

    @Override
    public String getOauthRedirectURL() {
        return "";
    }
}
