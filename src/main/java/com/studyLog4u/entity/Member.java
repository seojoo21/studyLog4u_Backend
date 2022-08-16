package com.studyLog4u.entity;

import com.studyLog4u.security.oauth2.enums.RoleType;
import com.studyLog4u.security.oauth2.enums.SocialLoginType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="member_board")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar2(200)", unique = true)
    private String email;

    @Column(columnDefinition = "varchar2(100)")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)", name="socialtype")
    private SocialLoginType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(15)", name="roletype")
    private RoleType roleType;

    public Member(String email, String nickname, SocialLoginType socialType, RoleType roleType) {
        this.email = email;
        this.nickname = nickname;
        this.socialType = socialType;
        this.roleType = roleType;
    }
}
