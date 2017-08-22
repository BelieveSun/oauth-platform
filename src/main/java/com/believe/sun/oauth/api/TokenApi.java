package com.believe.sun.oauth.api;

import com.believe.sun.oauth.util.ErrorCode;
import com.believe.sun.tool.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by sungj on 17-8-16.
 */
@RestController
@RequestMapping("/oauth/token")
public class TokenApi {
    @Autowired
    private DefaultTokenServices tokenService;

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity remove(Principal principal) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) ((OAuth2Authentication) principal).getDetails();
        String token = details.getTokenValue();
        if (tokenService.revokeToken(token)) {
            return ResultUtil.build(ErrorCode.SUCCESS);
        }
        return ResultUtil.build(ErrorCode.SERVICE_INNER_ERROR);
    }
}
