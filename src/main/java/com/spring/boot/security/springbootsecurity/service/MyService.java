package com.spring.boot.security.springbootsecurity.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
* @Description:    自定义权限控制
 *
* @Author:         tan_yi
* @CreateDate:     2021/11/30 16:50
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/30 16:50
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
public interface MyService  {

    /**
     * 根据用户权限判断是否有该请求URL的访问权限
     * @param request
     * @param authentication
     * @return
     */
    Boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
