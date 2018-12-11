package com.uqiansoft.gateway.constant;

import com.uqiansoft.gateway.config.HttpSupportConfig;

/**
 * 和shiro认证中心通信的一些配置
 * @author xutao
 * @date 2018-12-06 16:21
 */
public class ShiroConfig {

    public static final String AUTH_TOKEN = "usoft-session-id";

    public static final String HTTP_LOCAL = "http://localhost:" + HttpSupportConfig.PORT;

    public static final String CHECK_AUTH_URI = "/DATAHUBAUTHMANAGE-SERVICEA/user/checkAuth";
    public static final String CHECK_AUTH_URL = HTTP_LOCAL + CHECK_AUTH_URI;


}
