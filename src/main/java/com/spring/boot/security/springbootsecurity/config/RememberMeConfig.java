package com.spring.boot.security.springbootsecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
* @Description:    spring security 记住我功能
 * 实现几天内不用再次登录
* @Author:         tan_yi
* @CreateDate:     2021/12/1 18:05
* @UpdateUser:     tan_yi
* @UpdateDate:     2021/12/1 18:05
* @UpdateRemark:   修改内容
* @Version:        1.0
* @company:        newLand
*/
@Configuration
public class RememberMeConfig {

    /***/
    @Autowired
    private DataSource dataSource;

    /**
     * 指定rememberMe 登录时得到得token数据存储对象
     * 1. 通过内存存储 InMemoryTokenRepositoryImpl
     * 2. 通过数据库存储 JdbcTokenRepositoryImpl
     * @return
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // 第一次启动时自动建表。第一次启动需求，后续注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
