package com.believe.sun.oauth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sungj on 17-7-12.
 */
@RestController
public class TokenApi {
    @RequestMapping("/oauth/validate/token")
    public ResponseEntity vaildateToken(){
        return null;
    }
}
