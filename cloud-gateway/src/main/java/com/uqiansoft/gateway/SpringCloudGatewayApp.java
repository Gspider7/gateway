package com.uqiansoft.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xutao
 * @date 2018-11-26 09:28
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringCloudGatewayApp {


    public static void main(String[] args) {

        SpringApplication.run(SpringCloudGatewayApp.class, args);
    }
}
