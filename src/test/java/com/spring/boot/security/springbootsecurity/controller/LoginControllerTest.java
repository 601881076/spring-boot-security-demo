package com.spring.boot.security.springbootsecurity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class LoginControllerTest {

    @Test
    public void passwordEncoderTest() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("yanghuan");

        System.out.println(bCryptPasswordEncoder.matches("yanghuan",encode));
    }
}
