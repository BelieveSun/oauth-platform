package com.believe.sun.oauth.util;

import io.swagger.annotations.ApiModel;

/**
 * Created by sungj on 17-7-24.
 */
@ApiModel("result")
public class BaseResult implements Result {

    private Integer error;

    private String message;

    private String comment;

    public BaseResult() {
    }

    public BaseResult(Integer error) {
        this.error = error;
    }

    public BaseResult(Integer error, String message, String comment) {
        this.error = error;
        this.message = message;
        this.comment = comment;
    }



    @Override
    public Integer getError() {
        return error;
    }

    @Override
    public void setError(Integer error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }
}
