package com.security.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hu
 * @date 2019/3/15 17:53
 */

@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print("{\"code\":\"405\", \n \"msg\":\"用户未登录\"}");
        response.flushBuffer();
    }
}
