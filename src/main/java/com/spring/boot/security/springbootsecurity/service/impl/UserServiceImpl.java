package com.spring.boot.security.springbootsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
* @Description:    springSecurity 用户实现类，设定自定义登录逻辑
* @Author:         tan_yi
* @CreateDate:     2021/11/16 22:58
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/16 22:58
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 用户认证。
     * @param userName 用户名称
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("执行自定义登录逻辑");
        // 1. 根据用户名称查询用户
        if (!"admin".equals(userName)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 模拟获取数据库加密之后的密码
        String encode = passwordEncoder.encode("123");

        // 2. 比较用户密码是否正确
        passwordEncoder.matches("123",encode);

        // 3. 返回用户信息
        // springSecurity的角色命名有严格的要求，必须以ROLE_xxx命名，
        // 但是在SecurityConfig.java文件里面需要去掉ROLE_前缀
        return new User("admin", encode,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,test,ROLE_tanyi,/main.html"));
    }
}
