package com.studyLog4u.controller;

import com.studyLog4u.common.ApiDataResponse;
import com.studyLog4u.common.ApiDocumentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Tag(name="Login Controller", description = "로그인 관련 컨트롤러")
@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    /**
     * 프론트엔드 OAuth2Redirect 페이지에서의 로그인을 처리
     * OAuthController의 callback에서 프론트엔드에 cookie에 담아 보낸 jwtToken을 다시 백엔드로 받아와 시큐리티 인증 처리를 함
     * @param request
     * @return
     */
    @ApiDocumentResponse
    @Operation(summary = "프론트엔드 OAuth2Redirect 페이지에서의 로그인 처리", description = "프론트엔드 OAuth2Redirect 페이지에서 jwtToken을 백엔드로 보내어 로그인 처리를 담당하는 API 입니다.\n\n")
    @GetMapping(value="/login")
    public ApiDataResponse<String> login(HttpServletRequest request){
        log.info("LoginController Login Jwt in Header :: " + request.getHeader("Authorization"));
        String message = "로그인 성공";
        return new ApiDataResponse<>(message);
    }
}
