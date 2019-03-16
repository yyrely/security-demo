package com.security.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create By HU
 * Date 2019/3/15 20:16
 */

@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(403);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().print("{\"code\":\"403\", \n \"msg\":\"权限不足\"}");
        httpServletResponse.flushBuffer();
    }
}
