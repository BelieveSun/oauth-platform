package com.believe.sun.oauth.api;

import com.believe.sun.oauth.util.*;
import com.believe.sun.oauth.form.ClientForm;
import com.believe.sun.oauth.pojo.ClientInfo;
import com.believe.sun.oauth.service.OauthClientService;
import com.believe.sun.tool.DataResult;
import com.believe.sun.tool.ResultUtil;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
public class ClientApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OauthClientService oauthClientService;

    @ApiOperation("生成随机密码")
    @RequestMapping(value = "/random",method = RequestMethod.GET)
    public ResponseEntity<DataResult<String>> getRandomNumber() {
        String random = RandomStringUtils.random(10);
        String hex = DigestUtils.md5Hex(random);
        return ResultUtil.build(ErrorCode.SUCCESS, hex);
    }

    @ApiOperation(value = "创建客户端令牌")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @JsonView(ClientJsonView.Detail.class)
    public ResponseEntity<DataResult<ClientInfo>> registerClient(@RequestBody ClientForm form) {
        try {
            oauthClientService.registerClient(form);
            ClientInfo clientInfo = oauthClientService.getClientInfo(form.getClientId());
            return ResultUtil.build(ErrorCode.SUCCESS, clientInfo);
        } catch (ClientAlreadyExistsException e) {
            return ResultUtil.build(ErrorCode.CLIENT_EXIST);
        } catch (Exception e) {
            logger.error("Register client failed !", e);
            return ResultUtil.build(ErrorCode.SERVICE_INNER_ERROR);
        }
    }

    @ApiOperation(value = "获取客户端信息")
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
    @JsonView(ClientJsonView.Base.class)
    public ResponseEntity<DataResult<ClientInfo>> getClientInfo(@PathVariable String clientId) {
        ClientInfo clientInfo = oauthClientService.getClientInfo(clientId);
        return ResultUtil.build(ErrorCode.SUCCESS, clientInfo);
    }
}
