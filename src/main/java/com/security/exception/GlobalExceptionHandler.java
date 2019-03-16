package com.security.exception;

import javax.servlet.http.HttpServletResponse;

import com.security.handler.ResultBean;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hu
 * @date 2018/12/23 16:27
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletResponse response, Exception e) {
        log.error("exception info:", e);
        if (e instanceof ServiceException) {
            response.setStatus(((ServiceException) e).getHttpCode());
            return new ResultBean<>(e);
        } else {

            ServiceException exception = new ServiceException(BaseErrorCode.SYSTEM_ERROR, !StringUtils.isEmpty(e.getMessage()) ? e.getMessage() : BaseErrorCode.SYSTEM_ERROR.getMessage());
            response.setStatus(exception.getHttpCode());
            return new ResultBean<>(exception);
        }
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Object notFoundExceptionHandler() {
        return new ResultBean<>(new ServiceException(BaseErrorCode.API_NOT_FOUND));
    }



}
