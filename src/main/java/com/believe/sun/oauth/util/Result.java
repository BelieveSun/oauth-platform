package com.believe.sun.oauth.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sungj on 17-7-24.
 */
@ApiModel("result")
public interface Result {
    Integer getError();

    void setError(Integer error);

    String getMessage();

    void setMessage(String message);

    String getComment();

    void setComment(String comment);
}
