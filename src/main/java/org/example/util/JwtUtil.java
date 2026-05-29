package org.example.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {



//token解密
    public static Claims parseJWT(String secretKey, String token){
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成jwt令牌
     * @param claims
     * @param adminSecretKey
     * @param expMills
     * @return
     */
    public static String createJWT(Map<String, Object> claims, String adminSecretKey, long expMills) {
        //生成key,
        SecretKey key = Keys.hmacShaKeyFor(adminSecretKey.getBytes(StandardCharsets.UTF_8));
        //过期时间
        long expireTime = System.currentTimeMillis() + expMills;
        Date date = new Date(expireTime);
        String token = Jwts.builder()
                //signwith就是一键设置header和签名
                //SignatureAlgorithm.HS256指定了这个加密方法，头的类型type和签名算法会自动被系统配置
                //不用手写
                .signWith(SignatureAlgorithm.HS256, key)
                .claims(claims)//paylod载体
                .setExpiration(date)//过期时间
                .compact();
        return token;
    }
}
