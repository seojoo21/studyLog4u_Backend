package com.studyLog4u.security.oauth2.handler;

import com.studyLog4u.security.jwt.JwtTokenProvider;
import com.studyLog4u.security.oauth2.properties.AppProperties;
import com.studyLog4u.security.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.studyLog4u.security.oauth2.utils.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/**
 * oauth2 인증이 성공한 후에 실행되는 OAuth2AuthenticationSuccessHandler.
 * 프론트엔드를 통해서 들어온 redirect-uri가 저장해놓은 app.redirect-uris의 항목과 일치하는지 확인하고 일치한다면
 * token을 포함한 uri생성 후 인증요청 쿠키를 비워주고 redirect 하도록 한다.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if(response.isCommitted()){
            log.info("Response has already been committed. Unable to redirect to" + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * token 을 생성하고 이를 포함한 프론트엔드로의 uri를 생성한다.
     * @param request
     * @param response
     * @param authentication
     * @return
     */
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                                        .map(Cookie::getValue);
        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())){
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = jwtTokenProvider.createToken(authentication);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    /**
     * application.yml 에 등록해놓은 Redirect uri가 맞는지 확인한다.
     * @param uri
     * @return
     */
    private boolean isAuthorizedRedirectUri(String uri){
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                            .stream()
                            .anyMatch(authorizedRedirectUri -> {
                                URI authorizedURI = URI.create(authorizedRedirectUri);
                                if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                                        && authorizedURI.getPort() == clientRedirectUri.getPort()){
                                    return true;
                                }
                                return false;
                            });
    }
}
