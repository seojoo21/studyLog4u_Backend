package com.studyLog4u.security.oauth2.service.impl;

import com.studyLog4u.dto.MemberDto;
import com.studyLog4u.entity.Member;
import com.studyLog4u.repository.MemberRepository;
import com.studyLog4u.security.jwt.JwtTokenProvider;
import com.studyLog4u.security.jwt.JwtUserDetails;
import com.studyLog4u.security.oauth2.enums.RoleType;
import com.studyLog4u.security.oauth2.enums.SocialLoginType;
import com.studyLog4u.security.oauth2.exception.OAuthProviderMissMatchException;
import com.studyLog4u.security.oauth2.info.OAuth2UserInfo;
import com.studyLog4u.security.oauth2.info.OAuth2UserInfoFactory;
import com.studyLog4u.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 넘어온 사용자 로그인 요청에 socialLoginType에 맞춰 각각 다르게 userInfo를 실행해서 정보를 가져온다.
 * 회원 등록 여부에 따라서 회원 가입 혹은 업데이트를 진행한다.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOauth2UserService > getAttributes:" + super.loadUser(userRequest).getAttributes());
        OAuth2User user = super.loadUser(userRequest);
        //여기서 attriutes를 찍어보면 모두 각기 다른 이름으로 데이터가 들어오는 것을 확인할 수 있다.

        try{
            return process(userRequest, user);
        } catch(AuthenticationException ex) {
            throw new OAuth2AuthenticationException(ex.getMessage());
        } catch(Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    //인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이면 업데이트를 실행한다.
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        SocialLoginType socialLoginType = SocialLoginType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        //SocialLoginType에 따라서 각각 다르게 userInfo를 가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(socialLoginType, user.getAttributes());

        Member savedMember = memberRepository.findByEmail(userInfo.getEmail());
        if (savedMember != null) {
            if(socialLoginType != savedMember.getSocialType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + socialLoginType +
                                " account. Please use your " + savedMember.getSocialType() + " account to login."
                );
            }
            updateMember(savedMember, userInfo);
        } else {
            savedMember = createMember(userInfo, socialLoginType);
        }
        return new JwtUserDetails(savedMember, user.getAttributes());
    }

    private Member createMember(OAuth2UserInfo userInfo, SocialLoginType socialLoginType) {
        Member member = new Member(
            userInfo.getEmail(),
                userInfo.getName(),
                socialLoginType,
                RoleType.USER
        );
        return memberRepository.save(member);
    }

    private Member updateMember(Member member, OAuth2UserInfo userInfo){
        if (userInfo.getName() != null && !member.getNickname().equals(userInfo.getName())) {
            memberRepository.save(member);
        }
        return member;
    }



}
