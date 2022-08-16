package com.studyLog4u.security.oauth2.info;

import java.util.Map;

/**
 * 사용자 정보는 SocialLoginType에 따라서 각각 정보가 다른 이름으로 들어오게 된다.
 * 이를 구현해서 정보를 담기 위해 필요한 정보의 항목만 추상클래스로 만들어 놓고 타입마다 다르게 각각 추상클래스를 상속받는 클래스를 구현해서 정보를 가져온다.
 */
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();
    public abstract String getEmail();
    public abstract String getFirstName();
    public abstract String getLastName();

}
