package com.studyLog4u.entity;

import com.studyLog4u.oauth.helper.constants.RoleType;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="member_board")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(columnDefinition = "varchar2(100)", unique = true)
    private String email;

    @Column(columnDefinition = "varchar2(100)")
    private String password;

    @Column(columnDefinition = "varchar2(100)")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)", name="socialtype")
    private SocialLoginType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)", name="roletype")
    private RoleType roleType;

}
