package com.believe.sun.oauth.api;

import com.believe.sun.oauth.util.*;
import com.believe.sun.oauth.form.ClientForm;
import com.believe.sun.oauth.pojo.ClientInfo;
import com.believe.sun.oauth.service.OauthClientService;
import com.believe.sun.oauth.util.Error;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sungj on 17-6-12.
 */
@RestController
@RequestMapping("/clients")
public class OauthApi  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthClientService oauthClientService;

    @ApiOperation(value = "创建客户端令牌")
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<Result> registerClient(@RequestBody ClientForm form){
        try {
            oauthClientService.registerClient(form);
        }catch (ClientAlreadyExistsException e){
            return ResultUtil.build(ErrorCode.CLIENT_EXIST);
        }catch (Exception e) {
            logger.error("Register client failed !",e);
            return ResultUtil.build(ErrorCode.SERVICE_INNER_ERROR);
        }
        return ResultUtil.build(ErrorCode.SUCCESS);
    }

    @ApiOperation(value = "获取客户端信息")
    @RequestMapping(value = "/{clientId}",method = RequestMethod.GET)
    public ResponseEntity<DataResult<ClientInfo>> getClientInfo(@PathVariable String clientId){
        ClientInfo clientInfo = oauthClientService.getClientInfo(clientId);
        return ResultUtil.build(Error.SUCCESS,clientInfo);
    }
}
