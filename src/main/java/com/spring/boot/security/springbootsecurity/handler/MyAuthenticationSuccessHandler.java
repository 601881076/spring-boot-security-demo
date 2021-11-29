package com.spring.boot.security.springbootsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description:    自定义登录成功处理器
 * 通过实现AuthenticationSuccessHandler 来完成自定义登录成功地址
 *  跳转方式为redirect
* @Author:         tan_yi
* @CreateDate:     2021/11/29 22:32
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/29 22:32
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // 构建该类时必须传入一个url
    private final String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    /**
     *
     * @param request
     * @param response
     * @param authentication 当前登录认证对象
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 获取已授主体的权限列表
        System.out.println("权限列表");
        System.out.println(authentication.getAuthorities());

        // 获取凭证(密码) 基于安全考虑会输出null
        System.out.println("凭证");
        System.out.println(authentication.getCredentials());

        // 存储有关身份验证请求的其他详细信息。 这些可能是 IP 地址、证书序列号等。
        System.out.println("其他详细信息");
        System.out.println(authentication.getDetails());
        // 获取请求IP
        System.out.println(request.getRemoteAddr());

        // 被验证主体的身份。 在带有用户名和密码的身份验证请求的情况下，这将是用户名。 调用者应为身份验证请求填充主体。
        System.out.println("用户主体信息");
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        System.out.println(user.getPassword());

        // 登录成功重定向至该URL
        response.sendRedirect(url);
    }
}
