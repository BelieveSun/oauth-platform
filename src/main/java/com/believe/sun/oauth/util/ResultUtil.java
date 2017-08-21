package com.believe.sun.oauth.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by sungj on 17-7-24.
 */
public class ResultUtil {

    public static <T> ResponseEntity<DataResult<T>> build(Error error){
        return new ResponseEntity<>(new DataResult<T>(error.getError(),error.getMessage(),error.getComment()),
                HttpStatus.OK);
    }

    public static <T> ResponseEntity<DataResult<T>> build(Error error, T data){
        return new ResponseEntity<>(new DataResult<>(error.getError(),error.getMessage(),error.getComment(),data),
                HttpStatus.OK);

    }
}
