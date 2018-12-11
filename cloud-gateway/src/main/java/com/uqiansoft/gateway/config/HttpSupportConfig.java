package com.uqiansoft.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * webFlux支持http
 * @author xutao
 * @date 2018-11-22 15:17
 */
@Configuration
public class HttpSupportConfig {

    public static final int PORT = 7070;

    private HttpHandler httpHandler;

    private WebServer httpServer;

    @Autowired
    public HttpSupportConfig(HttpHandler httpHandler) {
        this.httpHandler = httpHandler;
    }

    @PostConstruct
    public void start() {
        ReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(PORT);
        this.httpServer = factory.getWebServer(this.httpHandler);
        this.httpServer.start();
    }

    @PreDestroy
    public void stop() {
        this.httpServer.stop();
    }
}
