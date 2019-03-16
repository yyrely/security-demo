package com.security.handler;

import java.io.Serializable;

import com.security.exception.ServiceException;

import lombok.Data;

/**
 * Created by Sion on 2017/9/2.
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public final static int SUCCESS = 1;
    public final static int FAIL = 0;

    private int code;
    private T data;
    private String msg;


    public ResultBean() {
        super();
        this.code = SUCCESS;
    }
    public ResultBean(T data) {
        super();
        this.code = SUCCESS;
        this.data = data;
    }
    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL ;
        if(e instanceof ServiceException){
            this.msg = e.getMessage();
            this.code = ((ServiceException) e).getCode();
        }
    }


}
