package com.xiuxiuguang.niubi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: SecurityConfig
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/9/27     lixiuguang    Create the current class
 *******************************************************************************/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] httpAntMatchers = {"/", "/login", "/logout", "/office/**"};
    private static final String[] webAntMatchers = {"/home/**", "/layui/**"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 允许post请求/add-user，而无需认证
                .antMatchers(httpAntMatchers).permitAll()
                // 所有请求都需要验证
                .anyRequest().authenticated()
                .and()
                // 使用默认的登录页面
                .formLogin()
                // 访问指定页面，用户未登入，跳转至登入页面，如果登入成功，跳转至用户访问指定页面，用户访问登入页面，默认的跳转页面
                .defaultSuccessUrl("/index")
                .and()
                // post请求要关闭csrf验证,不然访问报错；实际开发中开启，需要前端配合传递其他参数
                .csrf().disable();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(webAntMatchers);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}