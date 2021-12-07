package com.spring.boot.security.springbootsecurity.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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

    @Test
    public void getJwtToken() {
        JwtBuilder jwtBuilder = Jwts.builder()
                // 创建日期
                .setIssuedAt(new Date())
                // 主体，用户
                .setSubject("tanyi")
                // 声明的标识
                .setId("12345")
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
}
