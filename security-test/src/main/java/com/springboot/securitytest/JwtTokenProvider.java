package com.springboot.securitytest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}")
    private String secretKey;
    private final long tokenValidMillisecond = 1000L * 60 * 60; // 1시간
    private Key key;


    /**
     * 빈 등록 직후 비밀키 Base64 형태로 인코딩
     */
    @PostConstruct 
    protected void init(){
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        // Base64를 이용한 비밀키 인코딩
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    /**
     * 토큰 생성 메서드
     * @param userUid
     * @param roles
     * @return
     */
    public String createToken(String userUid, List<String> roles){
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userUid);// 회원 정보를 클레임에 담기
        claims.put("roles",roles);// 권한 정보 담기
        Date now = new Date();// 토큰 발급일

        byte[] keyBytes = secretKey.getBytes();
        key = Keys.hmacShaKeyFor(keyBytes);// signWith() Deprecated 되지 않은 메서드 사용을 위해서 사용

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now .getTime()+ tokenValidMillisecond))
                .signWith(key, SignatureAlgorithm.HS256) // 비밀키,암호화에 사용할 알고리즘
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");

        return token;
    }

    /**
     * 필터에서 토큰 인증 성공 시 SecurityContextHolder에 저장할 Authentication 을 생성 하는 기능.
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token){
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName: {}"
                ,userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    /**
     * token에서 username 정보 파싱하는 기능
     * @param token
     * @return
     */
    public String getUsername(String token){
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String username = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}",username);

        return username;
    }

    /**
     * HTTP 헤더에서 토큰 값을 가져오기 위한 기능
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request){

        LOGGER.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    /**
     * token 만료일 체크 기능
     * @param token
     * @return
     */
    public boolean validateToken(String token){
        LOGGER.info("[validateToken] 토큰 유효 체크 시작");

        try{
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());

        }catch(Exception e){
            LOGGER.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }

    }
}
