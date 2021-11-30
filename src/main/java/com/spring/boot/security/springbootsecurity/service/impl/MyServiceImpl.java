package com.spring.boot.security.springbootsecurity.service.impl;

import com.spring.boot.security.springbootsecurity.service.MyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @Description:    自定义权限控制实现类
 *
 * @Author:         tan_yi
 * @CreateDate:     2021/11/30 16:50
 * @UpdateUser:     tan_yi
 * @UpdateDate:     2021/11/30 16:50
 * @UpdateRemark:   修改内容
 * @Version:        1.0
 * @company:        newLand
 */
@Component
public class MyServiceImpl implements MyService {


    @Override
    public Boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 获取当前认证对象
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            // 获取当前对象登录权限列表
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            // 如果当前访问对象有该权限则允许通过。
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }

        return false;
    }
}
