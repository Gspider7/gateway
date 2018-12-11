package com.uqiansoft.gateway.config;

import com.uqiansoft.gateway.constant.ShiroConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域请求配置
 *
 * @author xutao
 * @date 2018-11-26 19:09
 */
@Configuration
public class CorsConfig {

    private static final String ALLOWED_HEADERS = "x-requested-with, Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE, credential, X-XSRF-TOKEN,"
            + ShiroConfig.AUTH_TOKEN;
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String MAX_AGE = "18000L";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();

                HttpHeaders requestHeaders = request.getHeaders();
                HttpHeaders responseHeaders = response.getHeaders();

                responseHeaders.add("Access-Control-Allow-Origin", requestHeaders.getOrigin());
                responseHeaders.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                responseHeaders.add("Access-Control-Max-Age", MAX_AGE);
                responseHeaders.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);

                responseHeaders.add("Access-Control-Allow-Credentials", "true");
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
}
