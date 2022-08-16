package com.studyLog4u.controller;

import com.studyLog4u.security.oauth2.service.OauthService;
import com.studyLog4u.security.oauth2.enums.SocialLoginType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Tag(name="Login Controller", description = "로그인 관련 컨트롤러")
@RestController
@RequestMapping("/api/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final OauthService oauthService;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param socialLoginType (GOOGLE)
     * @throws IOException
     */
    @GetMapping(value="/auth/{socialLoginType}")
    public void socialLoginRedirect(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType) throws IOException{
        log.info("SNS 로그인 요청:: {} Social Login", socialLoginType);
        oauthService.request(socialLoginType);
    }
}
