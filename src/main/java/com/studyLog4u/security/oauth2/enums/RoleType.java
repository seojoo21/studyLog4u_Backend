package com.studyLog4u.security.oauth2.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String roleName;
    RoleType(String roleName) {
        this.roleName = roleName;
    }
}
