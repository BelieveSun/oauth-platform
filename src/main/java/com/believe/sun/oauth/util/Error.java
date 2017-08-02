package com.believe.sun.oauth.util;

/**
 * Error definition：
 * 0: success
 * 1~1000: server error
 * 1001~2000: oauth service
 * 2001~3000: user service
 * Created by sungj on 17-7-24.
 */
public interface Error {

    Error SUCCESS = GlobalErrorCode.SUCCESS;
    Error SERVICE_INNER_ERROR = GlobalErrorCode.SERVICE_INNER_ERROR;

    Integer getError();


    String getMessage();

    String getComment();

    enum GlobalErrorCode implements Error{

        SUCCESS                          (0,"SUCCESS !"),
        SERVICE_INNER_ERROR              (500,"service inner error !","内部错误！");

        private Integer error;
        private String message;
        private String comment;

        GlobalErrorCode(Integer error, String message) {
            this.error = error;
            this.message = message;
        }

        GlobalErrorCode(Integer error, String message, String comment) {
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
}
