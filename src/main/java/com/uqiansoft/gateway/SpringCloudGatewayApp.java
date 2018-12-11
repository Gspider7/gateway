package com.uqiansoft.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xutao
 * @date 2018-11-26 09:28
 */
@SpringBootApplication
//@EnableCircuitBreaker                                   // 对webSocket不要使用断路器，如果要支持webSocket，不要使用全局断路器
@EnableEurekaClient                                     // 开启eureka客户端
public class SpringCloudGatewayApp {


    public static void main(String[] args) {

        SpringApplication.run(SpringCloudGatewayApp.class, args);
    }
}
