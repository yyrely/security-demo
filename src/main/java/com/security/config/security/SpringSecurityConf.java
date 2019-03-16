package com.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * @author Hu
 * @date 2019/3/14 10:34
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 权限鉴定过滤器
     */
    @Autowired
    private TokenAuthorizationFilter authorizationFilter;

    /**
     * 未登录结果处理
     */
    @Autowired
    private TokenAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 权限不足结果处理
     */
    @Autowired
    private TokenAccessDeniedHandler accessDeniedHandler;

    /**
     * 用户注销成功处理器
     */
    @Autowired
    private TokenLogoutSuccessHandler logoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //在UsernamePasswordAuthenticationFilter之前增加权限过滤器
        http.addFilterBefore(authorizationFilter, LogoutFilter.class);
        //用自定义的过滤器覆盖UsernamePasswordAuthenticationFilter
        http.addFilterAt(createTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        http    //关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                //未登录结果处理
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                //权限不足结果处理
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .authorizeRequests()
                //放行以下url
                .antMatchers("/users/register").permitAll()
                //给对应的url设置权限
                .antMatchers("/users/lala/**").hasRole("ADMIN")
                //所有请求都需要验证
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/users/logout").permitAll(false)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .formLogin();

    }

    /**
     * 登陆验证过滤器
     * @return
     * @throws Exception
     */
    private TokenAuthenticationFilter createTokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(authenticationManagerBean(), redisTemplate, objectMapper);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                //设置自己实现的userDetailsService（loadUserByUsername）
                .userDetailsService(userDetailsService)
                //设置密码加密方式
                .passwordEncoder(bCryptPasswordEncoder());
    }
}
