package com.believe.sun.oauth.service;

import com.believe.sun.oauth.form.ClientForm;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * Created by sungj on 17-6-12.
 */
public interface OauthClientService {

    void registerClient(ClientForm form);
}
