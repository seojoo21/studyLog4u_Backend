package com.studyLog4u.oauth.service.impl;

import com.studyLog4u.entity.Member;
import com.studyLog4u.jwt.JwtTokenService;
import com.studyLog4u.oauth.helper.constants.RoleType;
import com.studyLog4u.oauth.helper.constants.SocialLoginType;
import com.studyLog4u.oauth.model.GoogleOAuthRes;
import com.studyLog4u.oauth.model.GoogleOAuthToken;
import com.studyLog4u.oauth.model.GoogleUser;
import com.studyLog4u.oauth.service.SocialOAuth;
import com.studyLog4u.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthService {

    private final List<SocialOAuth> socialOauthList;
    private final HttpServletResponse response;
    private final GoogleOAuth googleOAuth;
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${sns.google.user-password}")
    private String googlePassword;

    public void request(SocialLoginType socialLoginType) {
        SocialOAuth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<String> requestAccessToken(SocialLoginType socialLoginType, String code) {
        SocialOAuth socialOAuth = this.findSocialOauthByType(socialLoginType);
        return socialOAuth.requestAccessToken(code);
    }

    public GoogleOAuthRes requestOAuthLogin(SocialLoginType socialLoginType, String code, HttpServletRequest request) throws Exception {
        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = this.requestAccessToken(socialLoginType, code);
        //응답 객체가 JSON 형식으로 되어 있으므로, 이를 역직렬화해서 자바 객체에 담을 것이다.
        GoogleOAuthToken oAuthToken = googleOAuth.getAccessToken(accessTokenResponse);
        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOAuth.requestUserInfo(oAuthToken);
        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOAuth.getUserInfo(userInfoResponse);

        String userId = googleUser.getId();
        // 서버와 대조하여 해당 user가 있는지 확인한다.
        // List<Member> list = memberRepository.findByUserId(userId);
        Member hasMember = memberRepository.findMemberByUserId(userId);

//        if(!list.isEmpty())
        if(hasMember != null){
            log.debug("member id :: " + hasMember.getUserId());
            log.debug("member nickname :: " + hasMember.getNickname());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, googlePassword, Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())));
            log.debug("authenticationToken :: " + authenticationToken);

            // 실제로 검증이 이루어지는 부분
            // authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            log.debug("authentication :: " + authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
            String jwt = jwtTokenService.createToken(authentication);
            log.debug("JWT ::" + jwt);

            //액세스 토큰과 jwt 토큰, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
            GoogleOAuthRes googleOAuthRes = new GoogleOAuthRes(jwt, oAuthToken.getAccess_token(), oAuthToken.getToken_type(), hasMember.getUserId(), hasMember.getNickname());

            return googleOAuthRes;
        } else {
            throw new Exception("존재하지 않는 회원입니다.");
        }
    }

    private SocialOAuth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
