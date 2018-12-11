package com.uqiansoft.gateway.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xutao
 * @date 2018-12-06 17:17
 */
@Controller
@RequestMapping("/local")
public class LocalController {

    @RequestMapping("/toLogin")
    public String toLogin(ServerHttpRequest request) {
        MultiValueMap<String, String> paramsMap = request.getQueryParams();

        return "login";
    }
}
