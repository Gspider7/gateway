package com.uqiansoft.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xutao
 * @date 2018-12-11 16:13
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    @RequestMapping("/check")
    public String checkAuth() throws InterruptedException {
        Thread.sleep(2000);

        return "auth success";
    }
}
