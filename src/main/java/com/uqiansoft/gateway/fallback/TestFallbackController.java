package com.uqiansoft.gateway.fallback;

import com.uqiansoft.gateway.constant.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xutao
 * @date 2018-11-26 10:40
 */
@Controller
public class TestFallbackController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ResponseBody
    @RequestMapping("/testFallback")
    public String testFallback() {
        Statistic.fallback_count.addAndGet(1);

        return "服务当前不可用";
    }



}
