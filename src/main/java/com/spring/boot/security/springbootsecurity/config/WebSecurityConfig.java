package com.spring.boot.security.springbootsecurity.config;

import com.spring.boot.security.springbootsecurity.handler.MyAuthenticationFailureHandler;
import com.spring.boot.security.springbootsecurity.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: security 配置类
 * @Author: tan_yi
 * @CreateDate: 2021/11/16 22:56
 * @UpdateUser: tan_yi
 * @UpdateDate: 2021/11/16 22:56
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 * @company: newLand
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * http请求处理
     * 包含拦截、登录页面跳转等
     * 一旦重写该方法，security将不再默认开启拦截。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单请求，必须是post请求
        http.formLogin()
                // 设置登录页面
                .loginPage("/login.html")
                // 当发现/login时认为是登录，必须和表单提交的地址一样。去执行UserServiceImpl
                // 这个方法可以看作一个中转站，前台界面提交表单之后跳转到这个路径进行User DetailsService的验证，
                // 如果成功， defaultSuccessUrl（）如果失败,那么转向failureUrl("/error.html")
                 .loginProcessingUrl("/login")

                // 登录成功后跳转页面，必须未POST请求
                // https://blog.csdn.net/qq_34975710/article/details/110232128
                // successForwardUrl 与 defaultForwardUrl
                // 当使用successForwardUrl时，对应controller 必须进行重定向返回
//                .successForwardUrl("/index");

                // 自定义登录成功跳转处理器(需搭配 loginProcesingUrl 方法使用)
                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))


                // 当使用defaultSuccessUrl时，第二个参数决定登录成功后是跳转至登录成功页面(true),
                // 还是跳转至原输入页面 (比如登录时输入一个不存在地址 -> 跳转登录页面 -> 不存在的地址)
//                .defaultSuccessUrl("/index", true)


                // 自定义登录失败跳转 aaa
//                .failureForwardUrl("/toError")
                // 自定义登录失败跳转处理器(同样也需要搭配loginProcesingUrl 方法使用)
                .failureHandler(new MyAuthenticationFailureHandler("https://blog.csdn.net/wangjunjun2008/article/details/9407219"))

                // 自定义登录用户名参数，必须和登录表单中的用户名称name一致
                .usernameParameter("username")
                // 自定义登录密码参数，必须和登录表单中的用户密码name一致
                .passwordParameter("password");


        // 设置拦截、认证、授权
        http.authorizeRequests()
                // 放行登录页面
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error.html").permitAll()
                /*
                ant 表达式
                ? : 匹配一个字符
                * : 匹配0个或多个字符
                ** : 匹配0个或多个目录
                 */
                .antMatchers("/**/*.jpg").permitAll()
                // 正则表达式匹配, 以下例子意思 = 放行method是get 且 后缀是png的 ?? 备注： 加上HtppMethod.Get 不能正确放行
//                .regexMatchers(HttpMethod.GET, ".+[.]png").permitAll()

                // 根据权限控制url 访问用户必须要有admiN（严格大小写）权限才能访问main1.html
                // .antMatchers("/main1.html").hasAuthority("admiN")
                // 拥有admiN, admin 权限的用户都可以访问main1.html
                // .antMatchers("/main1.html").hasAnyAuthority("admiN","admin")

                // 根据角色控制url，角色命名严格区分大小写且无法以ROLE开头
                // .antMatchers("/main1.html").hasRole("tanyi")
                // .antMatchers("/main1.html").hasAnyRole("tanyi,tanyi1")

                // 根据IP地址控制
                // .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                // 所有请求必须被认证通过后才能被访问
                .anyRequest().authenticated();

        // 关闭csrf防护
        http.csrf().disable();
    }

    /**
     * springSecurity 设置密码编码方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder setPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
