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
