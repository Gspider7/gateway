package com.uqiansoft.gateway.filters;

import com.alibaba.fastjson.JSON;
import com.uqiansoft.gateway.constant.ResponseData;
import com.uqiansoft.gateway.constant.ShiroConfig;
import com.uqiansoft.gateway.utils.OkhttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.client.HttpClientResponse;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取请求地址和请求token，判断该请求是否有权限，如果有权限执行转发，没有权限返回错误信息
 * 参考：https://my.oschina.net/tongyufu/blog/2120317
 * @author xutao
 * @date 2018-11-27 09:14
 */
@Component
public class RequestForwardFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders requestHeaders = request.getHeaders();

        // 如果请求地址不在白名单内
        String requestUri = request.getPath().pathWithinApplication().value();          // 请求地址，除去ip，port，context
        List<String> whiteList = getWhiteList();
        if (!whiteList.contains(requestUri)) {
            // 获取token
            String authToken = requestHeaders.getFirst(ShiroConfig.AUTH_TOKEN);
            if (authToken != null) {
                // 请求shiro服务器进行鉴权
                ResponseData checkResult = checkRequestAuth(authToken, requestUri);

                // 如果没有权限，返回错误信息
                if (!ResponseData.AUTHORITY_YES.equals(checkResult.getCode())) {
                    byte[] resultStream = JSON.toJSONString(checkResult).getBytes(Charset.forName("UTF-8"));
                    DataBuffer buffer = response.bufferFactory().wrap(resultStream);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }

//                // 重定向
//                String redirectUri = "/local/toLogin";
//                response.setStatusCode(HttpStatus.SEE_OTHER);
//                response.getHeaders().set(HttpHeaders.LOCATION, redirectUri);
//                return response.setComplete();
            }
        }

        // 分界方法，上面的代码是preFilter，下面的代码是postFilter
        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                // 获得请求响应
                HttpClientResponse clientResponse = exchange.getAttribute(ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR);
            })
        );
    }


    /**
     * 请求权限认证服务器，检查用户是否有访问指定uri的权限
     * @param token         单点登录的token
     * @param requestUri    请求uri
     */
    private ResponseData checkRequestAuth(String token, String requestUri) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(ShiroConfig.AUTH_TOKEN, token);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("url", requestUri);

        String checkResult = OkhttpUtil.sendOkhttpRequest(ShiroConfig.CHECK_AUTH_URL, JSON.toJSONString(paramsMap),
                OkhttpUtil.POST, headersMap);
        if (!StringUtils.isBlank(checkResult)) {
            try {
                return JSON.parseObject(checkResult, ResponseData.class);
            } catch (Exception e) {
                logger.error("无效的响应格式，response: {}", checkResult, e);
                return ResponseData.getDefault();
            }
        }
        return ResponseData.getDefault();
    }


    private List<String> getWhiteList() {
        List<String> whiteList = new ArrayList<>();

        whiteList.add(ShiroConfig.CHECK_AUTH_URI);

        return whiteList;
    }


    @Override
    public int getOrder() {
        return 0;                                   // 执行顺序在LoadBalancerClientFilter后面
    }
}
