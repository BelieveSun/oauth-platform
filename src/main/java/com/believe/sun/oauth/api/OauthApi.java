package com.believe.sun.oauth.api;

import com.believe.sun.oauth.form.ClientForm;
import com.believe.sun.oauth.service.OauthClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sungj on 17-6-12.
 */
@RestController
@RequestMapping("/register")
public class OauthApi  {

    @Autowired
    private OauthClientService oauthClientService;

    @ApiOperation(value = "创建客户端令牌")
    @RequestMapping(value = "/client",method = RequestMethod.POST)
    public ResponseEntity<String> registerClient(@RequestBody ClientForm form){
        try {
            oauthClientService.registerClient(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
