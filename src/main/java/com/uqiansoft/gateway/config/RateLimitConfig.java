package com.uqiansoft.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 请求限流配置
 * @author xutao
 * @date 2018-11-26 18:36
 */
@Configuration
public class RateLimitConfig {

    /**
     * 根据请求源ip地址限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 根据请求中附带信息：sessionId，用户id等限流
     */
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> {
            // 自己实现Mono.just()括号内的内容
            return Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
        };
    }

    /**
     * 根据请求地址/实际服务接口限流
     */
    @Bean
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}
