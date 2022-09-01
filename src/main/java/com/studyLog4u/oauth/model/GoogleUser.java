package com.studyLog4u.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 구글로 엑세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String locale;
}
