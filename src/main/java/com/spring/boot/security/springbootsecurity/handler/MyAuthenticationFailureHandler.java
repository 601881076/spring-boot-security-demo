package com.spring.boot.security.springbootsecurity.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description:    自定义登录失败处理器
 * 通过实现 AuthenticationFailureHandler 来完成自定义登录失败地址
 * 跳转方式为redirect
* @Author:         tan_yi
* @CreateDate:     2021/11/29 23:01
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/29 23:01
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String url;

    public MyAuthenticationFailureHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.sendRedirect(url);
    }
}
