package com.uqiansoft.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 把前端发过来的https请求，转换为http发送到微服务
 * 参考：https://www.jianshu.com/p/5a36129399f2
 * @author xutao
 * @date 2018-11-27 09:14
 */
@Component
public class SchemeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object uriObj = exchange.getAttributes().get(GATEWAY_REQUEST_URL_ATTR);
        if (uriObj != null) {
            // 只对https的请求，转变为http请求
            String scheme = ((URI)uriObj).getScheme();
            if ("https".equalsIgnoreCase(scheme)) {
                URI uri = (URI) uriObj;
                uri = this.upgradeConnection(uri, "http");
                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, uri);
            }
        }
        return chain.filter(exchange);
    }

    private URI upgradeConnection(URI uri, String scheme) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(uri).scheme(scheme);
        if (uri.getRawQuery() != null) {
            // When building the URI, UriComponentsBuilder verify the allowed characters and does not
            // support the '+' so we replace it for its equivalent '%20'.
            // See issue https://jira.spring.io/browse/SPR-10172
            uriComponentsBuilder.replaceQuery(uri.getRawQuery().replace("+", "%20"));
        }
        return uriComponentsBuilder.build(true).toUri();
    }

    @Override
    public int getOrder() {
        return 10101;                       // 执行顺序在LoadBalancerClientFilter后面
//        return -999;
    }
}
