package com.spring.boot.security.springbootsecurity.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @Description:    jjwtToken获取测试
 *
* @Author:         tan_yi
* @CreateDate:     2021/12/7 22:55
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/12/7 22:55
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@SpringBootTest
public class JwtTokenConfigTest {

    /**
     * 创建Token
     */
    @Test
    public void getJwtToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("sex","男");
        map.put("age",26);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 创建日期
                .setIssuedAt(new Date())
                // 主体，用户
                .setSubject("tanyi")
                // 声明的标识
                .setId("12345")
                // 使用setClaims之后，主体、创建日期、标识都不会保存再body了
                .setClaims(map)
                // 设置签名手段，参数1：算法， 参数2： 盐
                .signWith(SignatureAlgorithm.HS256, "secret");

        // 获得jwtToken字符串
        String jwtToken = jwtBuilder.compact();
        System.out.println(jwtToken);
        System.out.println("-----------");

        // 三部分的base64解密
        String[] jwtTokenArray = jwtToken.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(jwtTokenArray[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(jwtTokenArray[1]));
        // 无法解密，因为最后的签名部分时经过hs256加密算法
        System.out.println(Base64Codec.BASE64.decodeToString(jwtTokenArray[2]));

    }

    /**
     * 解析Token
     */
    @Test
    public void parseJwtToken() {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzZXgiOiLnlLciLCJhZ2UiOjI2fQ.cCp6gAIxV0_nfy3uPtfKTvtR23MsFdY6_rFlP0IxYiI";

        // 解析
        Jws<Claims> secret = Jwts.parser()
                // 放入盐key
                .setSigningKey("secret")
                // 解析token
                .parseClaimsJws(token);

        System.out.println(secret);
    }

    /**
     * 创建token -- 包含过期时间
     */
    @Test
    public void createJwtTokenHasExp() {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("sex","男");
        hashMap.put("age",26);
        hashMap.put("date",new Date());

        long nowDate = System.currentTimeMillis();
        // 一分钟后到期
        long expiration = nowDate + 60 * 1000;

        JwtBuilder secret = Jwts.builder()
                .setClaims(hashMap)
                // 设置过期时间
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS256, "secret");


        String token = secret.compact();

        System.out.println(token);
    }

    /**
     * 解析token
     */
    @Test
    public void parseTokenHasExpiration() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoxNjM5NDkwMTQ0MDY4LCJzZXgiOiLnlLciLCJleHAiOjE2Mzk0OTAyMDQsImFnZSI6MjZ9.sQm-LiOmYtVe-r9KZ1BloRlb4h42rNjoCDUoS4atpO0";

        Jws<Claims> secret = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token);

        // 获取到期时间
        Claims body = secret.getBody();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        System.out.println(secret);
        System.out.println("到期时间 = " + sdf.format(body.getExpiration()));
    }

















}
