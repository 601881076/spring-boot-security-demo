package com.spring.boot.security.springbootsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description:    自定义登出处理
* @Author:         tan_yi
* @CreateDate:     2021/12/1 21:31
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/12/1 21:31
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Component
public class MyLogoutHandler implements LogoutHandler {

    /**
     * 自定义登出处理
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("自定义登出处理器");
    }
}
