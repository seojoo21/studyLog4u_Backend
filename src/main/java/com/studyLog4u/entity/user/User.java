package com.studyLog4u.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studyLog4u.entity.BaseEntity;
import com.studyLog4u.oauth.entity.ProviderType;
import com.studyLog4u.oauth.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="user_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(columnDefinition = "varchar2(100)", unique = true)
    private String userId;

    @Column(columnDefinition = "varchar2(100)", unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Column(columnDefinition = "varchar2(200)", unique = true)
    private String email;

    @Column(columnDefinition = "varchar2(5)")
    private String emailVerifiedYn;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)")
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar2(20)")
    private RoleType roleType;

    public User(String userId, String username, String email, String emailVerifiedYn, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.userId = userId;
        this.username = username;
        this.password = "No_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}
