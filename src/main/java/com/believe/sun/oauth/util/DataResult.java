package com.believe.sun.oauth.util;

import io.swagger.annotations.ApiModel;

/**
 * Created by sungj on 17-7-24.
 */
@ApiModel("result")
public class DataResult<T> extends BaseResult {

    private T data;

    public DataResult(Integer error, T data) {
        super(error);
        this.data = data;
    }

    public DataResult(Integer error, String message, String comment, T data) {
        super(error, message, comment);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
