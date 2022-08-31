package com.studyLog4u.oauth.helper.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

@Getter
@AllArgsConstructor
public enum RoleType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String code;
}
