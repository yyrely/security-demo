package com.security.exception;

import lombok.Data;

/**
 * @author Hu
 * @date 2018/12/23 16:24
 */

@Data
public class ServiceException extends RuntimeException{

    private Integer code;
    private Integer httpCode;

    public ServiceException(){

    }

    public ServiceException(int code, int httpCode, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.httpCode = httpCode;
    }

    public ServiceException(int code, int httpCode, String message) {
        super(message);
        this.code = code;
        this.httpCode = httpCode;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.httpCode = errorCode.getHttpCode();
    }

    public ServiceException(ErrorCode errorCode , String message ) {
        super(message);
        this.code = errorCode.getCode();
        this.httpCode = errorCode.getHttpCode();
    }

}
