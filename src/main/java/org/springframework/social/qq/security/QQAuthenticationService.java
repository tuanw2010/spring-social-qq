package org.springframework.social.qq.security;

import org.springframework.social.qq.api.QQ;
import org.springframework.social.qq.connect.QQConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class QQAuthenticationService extends OAuth2AuthenticationService<QQ> {

    public QQAuthenticationService(String appId, String appSecret) {
        super(new QQConnectionFactory(appId, appSecret));
    }
}
