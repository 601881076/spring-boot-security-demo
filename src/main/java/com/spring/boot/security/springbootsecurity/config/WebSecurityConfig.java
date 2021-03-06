package com.spring.boot.security.springbootsecurity.config;

import com.spring.boot.security.springbootsecurity.handler.MyAccessDeniedHandler;
import com.spring.boot.security.springbootsecurity.handler.MyLogoutHandler;
import com.spring.boot.security.springbootsecurity.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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

    /** 自定义403访问受限处理器*/
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    /** 自定义登录逻辑*/
    @Autowired
    private UserServiceImpl userService;

    /** 用于存储用户登录令牌的持久层对象*/
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    /** 自定义登出成功逻辑*/
    @Autowired
    private MyLogoutHandler myLogoutHandler;

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
                .successForwardUrl("/index")

                // 自定义登录成功跳转处理器(需搭配 loginProcesingUrl 方法使用)
//                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))


                // 当使用defaultSuccessUrl时，第二个参数决定登录成功后是跳转至登录成功页面(true),
                // 还是跳转至原输入页面 (比如登录时输入一个不存在地址 -> 跳转登录页面 -> 不存在的地址)
//                .defaultSuccessUrl("/index", true)


                // 自定义登录失败跳转 aaa
                .failureForwardUrl("/toError")
                // 自定义登录失败跳转处理器(同样也需要搭配loginProcesingUrl 方法使用)
//                .failureHandler(new MyAuthenticationFailureHandler("https://blog.csdn.net/wangjunjun2008/article/details/9407219"))

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

                // 根据角色控制url，角色命名严格区分大小写且无法以ROLE开头 （当antMatchers 与 anyRequest().access()共存时，优先antMatchers的校验）
//                 .antMatchers("/main1.html").hasRole("tanyi")
                // .antMatchers("/main1.html").hasAnyRole("tanyi,tanyi1")

                // 根据IP地址控制
                // .antMatchers("/main1.html").hasIpAddress("127.0.0.1")
                // 所有请求必须被认证通过后才能被访问
                .anyRequest().authenticated()

                // 自定义逻辑
//                .anyRequest().access("@myServiceImpl.hasPermission(request, authentication)")
        ;

        // 异常处理
        http.exceptionHandling()
                // 自定义访问受限校验。设置访问受限后交给 myAccessDeniedHandler 对象进行处理。
                .accessDeniedHandler(myAccessDeniedHandler);

        // 实现rememberMe功能
        http.rememberMe()
            // 设定前端传入的name参数
//            .rememberMeParameter("remember")
            // 自定义失效时间，默认2周，单位S
//            .tokenValiditySeconds(60)
            // 自定义remember功能实现
//            .rememberMeServices()
            // 自定义登录逻辑
            .userDetailsService(userService)
            // 指定存储位置
            .tokenRepository(persistentTokenRepository)
        ;

        /**
         * 登出
         * spring Security登出主要做两件事情
         * 1. 清除httpSession
         * 2. 清除当前authentic认证对象。
         * */
        http.logout()
            // 设置登出的Url
            .logoutUrl("/userLogout")
            // 设置登出之后跳转的页面
            .logoutSuccessUrl("/login.html")
                // 自定义登出处理器
            .addLogoutHandler(myLogoutHandler)
        ;
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
