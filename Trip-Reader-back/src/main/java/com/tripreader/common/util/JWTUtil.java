package com.tripreader.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tripreader.common.exception.UnAuthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    public String expireAccessToken(String memberId) {
        return create(memberId, "access_token", 0);
    }

    public String expireRefreshToken(String memberId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("isRefresh", "true");
        return create(map, "refresh_token", 0);
    }

    public String createAccessToken(String memberId) {
        return create(memberId, "access_token", accessTokenExpireTime);
    }

    //	AccessToken에 비해 유효기간을 길게 설정.
    public String createRefreshToken(String memberId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("isRefresh", "true");
        return create(map, "refresh_token", refreshTokenExpireTime);
    }

    //	Token 발급
//		key : Claim에 셋팅될 key 값
//		value : Claim에 셋팅 될 data 값
//		subject : payload에 sub의 value로 들어갈 subject값
//		expire : 토큰 유효기간 설정을 위한 값
//		jwt 토큰의 구성 : header + payload + signature
    private String create(String memberId, String subject, long expireTime) {
//		Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration),
//		토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
        Claims claims = Jwts.claims().setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .setIssuedAt(new Date()) // 생성일 설정
//				만료일 설정 (유효기간)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));

//		저장할 data의 key, value
        claims.put("memberId", memberId);

        String jwt = Jwts.builder()
//			Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE).setClaims(claims)
//			Signature 설정 : secret key를 활용한 암호화.
                .signWith(SignatureAlgorithm.HS256, this.generateKey()).compact(); // 직렬화 처리.

        return jwt;
    }

    //	Token 발급
//		key : Claim에 셋팅될 key 값
//		value : Claim에 셋팅 될 data 값
//		subject : payload에 sub의 value로 들어갈 subject값
//		expire : 토큰 유효기간 설정을 위한 값
//		jwt 토큰의 구성 : header + payload + signature
    private String create(Map<String,String> claimsMap, String subject, long expireTime) {
//		Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration),
//		토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
        Claims claims = Jwts.claims().setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .setIssuedAt(new Date()) // 생성일 설정
//				만료일 설정 (유효기간)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime));

//		저장할 data의 key, value
        for (Map.Entry<String, String> entry : claimsMap.entrySet()) {
            claims.put(entry.getKey(), entry.getValue());
        }

        String jwt = Jwts.builder()
//			Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE).setClaims(claims)
//			Signature 설정 : secret key를 활용한 암호화.
                .signWith(SignatureAlgorithm.HS256, this.generateKey()).compact(); // 직렬화 처리.

        return jwt;
    }

    //	Signature 설정에 들어갈 key 생성.
    private byte[] generateKey() {
        byte[] key = null;
        try {
//			charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
            key = salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        return key;
    }

    //	전달 받은 토큰이 제대로 생성된 것인지 확인 하고 문제가 있다면 UnauthorizedException 발생.
    public boolean checkToken(String token) {
        try {
//			Json Web Signature? 서버에서 인증을 근거로 인증 정보를 서버의 private key 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(token);
//			Claims 는 Map 구현체 형태

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getMemberId(String authorization) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(authorization);
        } catch (Exception e) {
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        return (String) value.get("memberId");
    }

    public boolean isRefreshToken(String authorization) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parserBuilder().setSigningKey(this.generateKey()).build().parseClaimsJws(authorization);
        } catch (Exception e) {
            throw new UnAuthorizedException();
        }
        Map<String, Object> value = claims.getBody();
        return value.containsKey("isRefresh") && value.get("isRefresh").equals("true");
    }

}
