package com.uqiansoft.gateway.config;

/**
 * 和shiro认证中心通信的一些配置
 * @author xutao
 * @date 2018-12-06 16:21
 */
public class AuthConfig {

    public static final String HTTP_LOCAL = "http://localhost:" + HttpSupportConfig.PORT;

    public static final String CHECK_AUTH_URI = "/HTTP-TEST-SERVER/auth/check";
    public static final String CHECK_AUTH_URL = HTTP_LOCAL + CHECK_AUTH_URI;


}
