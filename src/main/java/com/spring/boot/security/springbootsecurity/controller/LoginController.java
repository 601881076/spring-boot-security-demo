package com.spring.boot.security.springbootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description:    登录控制器
* @Author:         tan_yi
* @CreateDate:     2021/11/17 21:25
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/11/17 21:25
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Controller
public class LoginController {

    @RequestMapping(value = "/index")
    // 角色权限方法控制，以下注解表示只有拥有ROLE_tanyi1角色的权限才有访问/index的权限
//    @Secured("ROLE_tanyi1")

    // 表示方法或类执行结束后判断权限，此注解很少被使用到
//    @PostAuthorize("")

    // 表示访问方法或类在执行之前先判断权限，大多情况下都是使用这个注解，注解的参数和access()方法参数取值相同，都是权限表达式
    // 支持忽略ROLE_, 也支持加上ROLE_yh
//    @PreAuthorize("hasAnyRole('tanyi','ROLE_yh')")
    public String index() {
        System.out.println("index");
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String error() {
        System.out.println("登录失败跳转");
        return "redirect:error.html";
    }
}
