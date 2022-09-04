package com.studyLog4u.oauth.controller;

import com.studyLog4u.common.ApiDataResponse;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import com.studyLog4u.oauth.model.GoogleOAuthRes;
import com.studyLog4u.oauth.service.impl.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final OAuthService oauthService;


    @GetMapping("/api/auth/{socialLoginType}")
    public void socialLoginType(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType){
        log.info("OauthController 사용자로부터 소셜 로그인 요청 받음 :: {}", socialLoginType);
        oauthService.request(socialLoginType);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginType (GOOGLE)
     * @param code API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */

    // 오리지널 코드
//    @GetMapping("/oauth2/callback/{socialLoginType}")
//    public String callback(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType,
//                           @RequestParam(name="code") String code){
//        log.info("OauthController 소셜 로그인 API 서버로부터 받은 code :: {}", code);
//        return oauthService.requestAccessToken(socialLoginType, code);
//    }

    // 반환 타입 바꾼 코드
//    @GetMapping("/oauth2/callback/{socialLoginType}")
//    public ApiDataResponse<String> callback(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType,
//                                    @RequestParam(name="code") String code){
//        log.info("OauthController 소셜 로그인 API 서버로부터 받은 code :: {}", code);
//        return new ApiDataResponse<>(oauthService.requestAccessToken(socialLoginType, code));
//    }

    // 반환 타입 바꾼 코드
    @GetMapping("/oauth2/callback/{socialLoginType}")
    public ApiDataResponse<String> callback(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType,
                                    @RequestParam(name="code") String code) throws Exception {
        log.info("OauthController 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        GoogleOAuthRes googleOAuthRes = oauthService.requestOAuthLogin(socialLoginType, code);
        return new ApiDataResponse<>(googleOAuthRes);
    }
}
