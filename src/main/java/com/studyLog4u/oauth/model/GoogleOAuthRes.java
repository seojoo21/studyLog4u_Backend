package com.studyLog4u.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 클라이언트로 보낼 jwtToken, accessToken 등이 담긴 객체
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleOAuthRes {
    private String jwtToken;
    private String accessToken;
    private String tokenType;

    /* 20220912 추가 */
    private String memberId;
    private String memberName;
}
