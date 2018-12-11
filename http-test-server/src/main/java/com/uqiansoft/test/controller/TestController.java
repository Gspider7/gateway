package com.uqiansoft.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xutao
 * @date 2018-12-11 16:53
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String helloWorld() {

        return "hello, world";
    }
}
