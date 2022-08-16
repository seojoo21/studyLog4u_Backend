package com.studyLog4u.dto;

import com.studyLog4u.security.oauth2.enums.RoleType;
import com.studyLog4u.security.oauth2.enums.SocialLoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private SocialLoginType socialType;
    private RoleType roleType;
    private LocalDateTime regDate, moDate;
}
