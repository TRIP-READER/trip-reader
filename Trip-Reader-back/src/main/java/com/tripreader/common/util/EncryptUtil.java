package com.tripreader.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptUtil {
    @Value("${sha.salt}")
    public static String salt;

    public static String getEncrypt(String string) {
        return doEncrypt(string, salt);
    }
    private static String doEncrypt(String pwd, String salt) {
        String result = "";
        try {
            //1. SHA256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //2. 비밀번호와 salt 합친 문자열에 SHA 256 적용
            md.update((pwd+salt).getBytes());
            byte[] pwdsalt = md.digest();

            //3. byte To String (10진수의 문자열로 변경)
            StringBuffer sb = new StringBuffer();
            for (byte b : pwdsalt)
                sb.append(String.format("%02x", b));

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
