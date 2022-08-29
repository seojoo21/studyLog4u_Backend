package com.studyLog4u.entity;

import com.studyLog4u.oauth.helper.constants.RoleType;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="member_board")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(columnDefinition = "varchar2(100)", name="nickname")
    private String nickname;

    @Column(columnDefinition = "varchar2(100)", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)", name="socialtype")
    private SocialLoginType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)", name="roletype")
    private RoleType roleType;

}
