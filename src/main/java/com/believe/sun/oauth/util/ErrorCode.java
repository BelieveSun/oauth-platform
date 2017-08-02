package com.believe.sun.oauth.util;

/**
 * Created by sungj on 17-7-26.
 */
public enum ErrorCode implements Error {

    CLIENT_EXIST                     (1001,"client id exist !","客户端已存在！");


    private Integer error;
    private String message;
    private String comment;

    ErrorCode(Integer error, String message) {
        this.error = error;
        this.message = message;
    }

    ErrorCode(Integer error, String message, String comment) {
        this.error = error;
        this.message = message;
        this.comment = comment;
    }

    @Override
    public Integer getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getComment() {
        return comment;
    }


}
