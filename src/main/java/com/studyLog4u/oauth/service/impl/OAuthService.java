package com.studyLog4u.oauth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import com.studyLog4u.oauth.model.GoogleOAuthRes;
import com.studyLog4u.oauth.model.GoogleOAuthToken;
import com.studyLog4u.oauth.model.GoogleUser;
import com.studyLog4u.oauth.service.SocialOAuth;
import com.studyLog4u.oauth.service.impl.GoogleOAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final List<SocialOAuth> socialOauthList;
    private final HttpServletResponse response;
    private final GoogleOAuth googleOAuth;

    public void request(SocialLoginType socialLoginType) {
        SocialOAuth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 오리지널 코드
//    public String requestAccessToken(SocialLoginType socialLoginType, String code) {
//        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
//        return socialOauth.requestAccessToken(code);
//    }

    // 반환 타입 바꾼 코드
    public ResponseEntity<String> requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOAuth socialOAuth = this.findSocialOauthByType(socialLoginType);
        return socialOAuth.requestAccessToken(code);
    }

    // 수정
    public GoogleOAuthRes requestOAuthLogin(SocialLoginType socialLoginType, String code) throws JsonProcessingException {
        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = this.requestAccessToken(socialLoginType, code);
        //응답 객체가 JSON 형식으로 되어 있으므로, 이를 역직렬화해서 자바 객체에 담을 것이다.
        GoogleOAuthToken oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOAuth.getUserInfo(userInfoResponse);
        return null;
    }

    private SocialOAuth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
