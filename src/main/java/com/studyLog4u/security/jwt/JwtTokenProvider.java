package com.studyLog4u.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 패키지를 생성하고 토큰의 생성과 토큰의 유효성 검증 등을 담당하는 클래스
 */
@Slf4j
@PropertySource("classpath:application-oauth.yml")
@Service
public class JwtTokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secretKey;
    private final long tokenValidityInMilliseconds;

    private Key key;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
    }

    /**
     * Bean이 생성되고 의존성 주입을 받은 후에 secretKey 값을 Base64 Decode 해서 key 변수에 할당합니다.
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Authentication 객체의 권한 정보를 이용해서 토큰을 생성
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication){
        log.info("JwtTokenProvider : createToken");
        log.info(String.valueOf(authentication));

        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();

    }


    /**
     * token을 매개변수로 받아서 토큰에 담긴 정보를 이용해 Authenticaion 객체를 리턴
     * token으로 클레임을 만들고, 클레임에서 권한정보를 받아서 유저 객체를 만들어서 최종적으로 Authenticaion 객체를 리턴
     * 참고) Claims : JWT 의 속성정보, java 에서 Claims 는 Json map 형식의 인터페이스임
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token){
        log.info("JwtTokenProvider : getAuthentication");

        Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(),"",authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * token을 매개변수로 받아서 토큰의 유효성 검증을 수행
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        log.info("JwtTokenProvider : validateToken");

        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e){
            log.error("잘못된 JWT 서명입니다. : " + e);
        } catch(ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다. : " + e);
        } catch(UnsupportedJwtException e){
            log.error("지원되지 않는 JWT 토큰입니다. : " + e);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
