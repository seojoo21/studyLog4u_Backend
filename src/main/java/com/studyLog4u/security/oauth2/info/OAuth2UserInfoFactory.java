package com.studyLog4u.security.oauth2.info;

import com.studyLog4u.security.oauth2.info.impl.GoogleOAuth2UserInfo;
import com.studyLog4u.security.oauth2.enums.SocialLoginType;

import java.util.Map;

/**
 * switch를 통해서 SocialLoginType에 따라서 각각 다른 userInfo 정보를 가져오도록 하는 팩토리 클래스
 */
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(SocialLoginType socialLoginType, Map<String, Object> attributes){
     switch (socialLoginType) {
         case GOOGLE : return new GoogleOAuth2UserInfo(attributes);
         default: throw new IllegalArgumentException("소셜 로그인 타입이 유효하지 않습니다.");
     }
    }
}
