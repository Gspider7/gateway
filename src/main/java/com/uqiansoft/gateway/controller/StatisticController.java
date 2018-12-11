package com.uqiansoft.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.uqiansoft.gateway.constant.Statistic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xutao
 * @date 2018-11-27 16:26
 */
@Controller
@RequestMapping("/statistic")
public class StatisticController {


    @ResponseBody
    @RequestMapping("/get")
    public String get() {
        Map<String, Integer> countMap = new HashMap<>();

        countMap.put("req_count", Statistic.req_count.get());
        countMap.put("fallback_count", Statistic.fallback_count.get());

        // 获取以后自动清空统计
        Statistic.req_count.set(0);
        Statistic.fallback_count.set(0);

        return JSON.toJSONString(countMap);
    }
}
