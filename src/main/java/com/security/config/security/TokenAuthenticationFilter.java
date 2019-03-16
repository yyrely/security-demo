package com.security.config.security;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.security.entity.Users;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Hu
 * @date 2019/3/14 17:46
 */

public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper;

    private AuthenticationManager authenticationManager;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        super.setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            //从body中取出
            Users users = objectMapper.readValue(request.getInputStream(), Users.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SUser user = (SUser) authResult.getPrincipal();
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("SECURITY_TOKEN : " + uuid, user.getUsername());
        redisTemplate.opsForValue().set("SECURITY_USERNAME : " + user.getUsername(), uuid);
        redisTemplate.expire("SECURITY_TOKEN : " + uuid, 60, TimeUnit.MINUTES);
        redisTemplate.expire("SECURITY_USERNAME : " + user.getUsername(), 60, TimeUnit.MINUTES);

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\n" +
                "    \"code\": 1,\n" +
                "    \"data\": {\n" +
                "        \"username\": \""+user.getUsername()+"\",\n" +
                "        \"token\": \""+uuid+"\"\n" +
                "    }\n" +
                "}");
        response.flushBuffer();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(400);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\"code\":\"400\", \n \"msg\":\"用户名或密码不正确，登陆失败\"}");
        response.flushBuffer();
    }
}
