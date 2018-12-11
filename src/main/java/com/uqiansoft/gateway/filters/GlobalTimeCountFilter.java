package com.uqiansoft.gateway.filters;

import com.uqiansoft.gateway.constant.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局过滤器，对所有路由都生效，只需注入spring就会生效
 * @author xutao
 * @date 2018-11-26 17:56
 */
@Component
public class GlobalTimeCountFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();

        String requestUri = request.getURI().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS :");

        Date startTime = new Date();
        logger.debug("请求: {}, 开始执行时间: {}", requestUri, sdf.format(startTime));
        Statistic.req_count.addAndGet(1);

        // 分界方法，上面的代码是preFilter，下面的代码是postFilter
        return chain.filter(exchange).then(
            Mono.fromRunnable(() -> {
                Date endTime = new Date();
                logger.debug("请求: {}, 结束时间: {}", requestUri, sdf.format(endTime));
                logger.debug("请求: {}, 消耗: {}ms", requestUri, endTime.getTime() - startTime.getTime());
            })
        );
    }

    @Override
    public int getOrder() {
        return -1000;
    }
}
