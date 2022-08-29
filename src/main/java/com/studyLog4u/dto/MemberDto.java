package com.studyLog4u.dto;

import com.studyLog4u.oauth.helper.constants.RoleType;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
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
    private Long userSeq;
    private String nickname;
    private String email;
    private SocialLoginType socialLoginType;
    private RoleType roleType;
    private LocalDateTime regDate, modDate;
}
