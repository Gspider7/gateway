package com.uqiansoft.gateway.filters;

import com.uqiansoft.gateway.config.AuthConfig;
import com.uqiansoft.gateway.utils.OkhttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xutao
 * @date 2018-11-27 09:14
 */
@Component
public class RequestForwardFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestUri = request.getPath().pathWithinApplication().value();
        if (AuthConfig.CHECK_AUTH_URI.equals(requestUri)) {
            return chain.filter(exchange);
        }

        OkhttpUtil.post(AuthConfig.CHECK_AUTH_URL);

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
