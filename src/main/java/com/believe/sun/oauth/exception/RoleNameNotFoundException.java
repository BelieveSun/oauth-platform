package com.believe.sun.oauth.exception;

/**
 * Created by sungj on 17-6-19.
 */
public class RoleNameNotFoundException extends RuntimeException {
    public RoleNameNotFoundException() {
    }

    public RoleNameNotFoundException(String s) {
        super(s);
    }

    public RoleNameNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RoleNameNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public RoleNameNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
