package com.uqiansoft.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xutao
 * @date 2018-11-26 09:28
 */
@SpringBootApplication
@EnableEurekaClient
public class HttpTestServerApp {


    public static void main(String[] args) {

        SpringApplication.run(HttpTestServerApp.class, args);
    }
}
