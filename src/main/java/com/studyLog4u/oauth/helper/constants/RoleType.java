package com.studyLog4u.oauth.helper.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    // 스크링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야 한다.
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String code;
}
