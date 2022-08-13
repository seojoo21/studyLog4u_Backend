package com.studyLog4u.dto;

import com.studyLog4u.oauth.SocialLoginType;
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
    private LocalDateTime regDate, moDate;
}
