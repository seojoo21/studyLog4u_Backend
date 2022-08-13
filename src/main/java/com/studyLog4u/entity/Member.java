package com.studyLog4u.entity;

import com.studyLog4u.oauth.SocialLoginType;
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
}
