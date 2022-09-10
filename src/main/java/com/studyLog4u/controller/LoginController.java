package com.studyLog4u.controller;

import com.studyLog4u.common.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    @RequestMapping(value="")
    public ApiDataResponse<String> login(HttpServletRequest request){
        log.info("LoginController Jwt in Header :: " + request.getHeader("Authorization"));
        String message = "로그인 성공";
        return new ApiDataResponse<>(message);
    }
}
