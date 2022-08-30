package com.studyLog4u.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 클라이언트로 보낼 jwtToken, accessToken 등이 담긴 객체
 */
@AllArgsConstructor
@Getter
@Setter
public class GoogleOAuthRes {
    private String jwtToken;
    private int userSeq;
    private String accessToken;
    private String tokenType;
}
