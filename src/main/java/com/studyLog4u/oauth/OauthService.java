package com.studyLog4u.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {

    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;

    public void request(SocialLoginType socialLoginType){
        String redirectURL = "";
        //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해준다.
        switch (socialLoginType) {
            case GOOGLE: {
                redirectURL = googleOauth.getOauthRedirectURL();
            } break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }

        try{
            response.sendRedirect(redirectURL);
        } catch( IOException e){
            e.printStackTrace();
        }
    }
}
