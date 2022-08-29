package com.studyLog4u.oauth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyLog4u.oauth.model.GoogleOAuthToken;
import com.studyLog4u.oauth.model.GoogleUser;
import com.studyLog4u.oauth.service.SocialOAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleOAuth implements SocialOAuth {

    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client-id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.client-secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.redirect-uri}")
    private String GOOGLE_SNS_REDIRECT_URI;
    @Value("${sns.google.token-base-url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;
    @Value("${sns.google.userinfo-request-url}")
    private String GOOGLE_SNS_USERINFO_REQUEST_URL;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_REDIRECT_URI);
        params.put("access_type", "offline");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        String redirectUrl = GOOGLE_SNS_BASE_URL + "?" + parameterString;

        return redirectUrl;
    }

    // 오리지널 코드
//    @Override
//    public String requestAccessToken(String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
//        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
//        params.put("redirect_uri", GOOGLE_SNS_REDIRECT_URI);
//        params.put("grant_type", "authorization_code");
//
//        ResponseEntity<String> responseEntity =
//                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);
//
//        if(responseEntity.getStatusCode() == HttpStatus.OK) {
//            return responseEntity.getBody();
//        }
//        return "구글 로그인 요청 처리 실패";
//    }

    // 반환 타입 바꾼 코드
    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_REDIRECT_URI);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }
        return null;
    }

    /**
     * responseEntity에 담긴 JSON String을 역직렬화해 자바 객체에 담는다.
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    public GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("GoogleOAuth response.getBody() :: " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleOAuthToken googleOAuthToken = objectMapper.readValue(response.getBody(),GoogleOAuthToken.class);
        return googleOAuthToken;
    }

    /**
     * 구글로 엑세스 토큰을 보내 구글 사용자 정보를 받아온다.
     * @param oAuthToken
     * @return
     */
    public ResponseEntity<String> requestUserInfo(GoogleOAuthToken oAuthToken){

        // header에 accessToken을 담는다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+oAuthToken.getAccess_token());

        //HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 한다.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_SNS_USERINFO_REQUEST_URL, HttpMethod.GET, request, String.class);

        return response;
    }

    /**
     * 구글 유저 정보가 담긴 JSON 문자열을 파싱하여 GoogleUser 객체에 담아준다.
     * @param userInfoRes
     * @return
     * @throws JsonProcessingException
     */
    public GoogleUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUser googleUser = objectMapper.readValue(userInfoRes.getBody(), GoogleUser.class);
        return googleUser;
    }
}
