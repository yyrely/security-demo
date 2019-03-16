package com.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Create By HU
 * Date 2019/3/15 20:48
 */

@Component
public class TokenLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SUser user = (SUser) authentication.getPrincipal();
        String token = redisTemplate.opsForValue().get("SECURITY_USERNAME : " + user.getUsername());
        redisTemplate.expire("SECURITY_USERNAME : " + user.getUsername(), 0, TimeUnit.MICROSECONDS);
        redisTemplate.expire("SECURITY_TOKEN : " + token, 0, TimeUnit.MICROSECONDS);

        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\"code\":\"1\", \n \"msg\":\"用户退出成功\"}");
        response.flushBuffer();
    }
}
